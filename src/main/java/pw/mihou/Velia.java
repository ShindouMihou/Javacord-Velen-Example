package pw.mihou;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.slf4j.LoggerFactory;
import pw.mihou.commands.*;
import pw.mihou.database.RedisDB;
import pw.mihou.velen.interfaces.Velen;
import pw.mihou.velen.interfaces.VelenCommand;
import pw.mihou.velen.interfaces.messages.types.VelenConditionalMessage;
import pw.mihou.velen.interfaces.messages.types.VelenPermissionMessage;
import pw.mihou.velen.interfaces.messages.types.VelenRatelimitMessage;
import pw.mihou.velen.internals.VelenBlacklist;
import pw.mihou.velen.prefix.VelenPrefixManager;
import pw.mihou.velen.ratelimiter.entities.RatelimitInterceptorPosition;

import java.awt.*;
import java.time.Duration;
import java.util.stream.Collectors;

public class Velia {

    // Create the Velen instance with
    // a custom rate-limited message and a blacklist.
    public static final Velen velen = Velen.builder()
            .setDefaultCooldownTime(Duration.ofSeconds(5))
            .setRatelimitedMessage(VelenRatelimitMessage.ofEmbed((remainingSeconds, user, channel, command) ->
                    new EmbedBuilder().setTitle("You are currently rate-limited!")
            .setDescription("Please wait " + remainingSeconds + " seconds before executing this command again!")
            .setColor(Color.RED)))
            .setNoPermissionMessage(VelenPermissionMessage.ofEmbed((permission, user, channel, command) ->
                    new EmbedBuilder().setTitle("Forbidden execution!")
            .setDescription("You do not have the permissions required to run this command!")
            .addField("Permissions", permission.stream().map(PermissionType::name)
            .map(s -> "`" + s + "`").collect(Collectors.joining(", ")))))
            .setPrefixManager(new VelenPrefixManager("ve.",
                    key -> RedisDB.get("prefix-"+key),
                    pair -> RedisDB.set("prefix-"+pair.getLeft(), pair.getRight())))
            .setBlacklist(new VelenBlacklist(aLong -> RedisDB.exists("blacklist"+aLong)))
            .build();

    public static final Logger log = (Logger) LoggerFactory.getLogger("Velia");

    public static void main(String[] args) {
        // We'll be setting the log level to DEBUG to gather
        // more information about what happens with Velen.
        log.setLevel(Level.DEBUG);

        // Let us ping Redis first.
        RedisDB.ping();

        // Notify us when the user runs a command (and also when the user is rate-limited).
        velen.getRatelimiter().addInterceptor(ratelimitObject ->
                log.debug("User {} ran and is now rate-limited on the command {}.", ratelimitObject.getUser(),
                        ratelimitObject.getCommand()), RatelimitInterceptorPosition.EXECUTION);

        // Notify us when the user is released from the rate-limit.
        velen.getRatelimiter().addInterceptor(ratelimitObject ->
                log.debug("User {} was released from rate-limit on {}.", ratelimitObject.getUser(),
                        ratelimitObject.getCommand()), RatelimitInterceptorPosition.RELEASE);


        // Add all the commands to the Velen instance.
        createCommands();

        // Create the DiscordApi instance with the specific intents.
        final DiscordApi api = new DiscordApiBuilder()
                .setToken(System.getenv("token"))
                .setIntents(Intent.GUILDS, Intent.GUILD_MEMBERS, Intent.GUILD_MESSAGES, Intent.GUILD_MESSAGE_REACTIONS,
                        Intent.DIRECT_MESSAGES, Intent.DIRECT_MESSAGE_REACTIONS)
                .addListener(velen)
                .login()
                .join();

        log.info("Connected to bot [{}] with id [{}] and size [{} servers]", api.getYourself().getName(), api.getYourself().getId(),
                api.getServers().size());
    }

    /**
     * Initialize all the commands
     * that will be attached to the Velen instance.
     */
    private static void createCommands() {
        // Create a simple ping command that measures the
        // latency of the bot through sending a message and editing the message.
        VelenCommand.of("ping", "A simple ping command that measures the latency of the bot.", velen, new PingCommand())
                .addShortcuts("pong", "pang")
                .setUsage("ping")
                .setCategory("Utility")
                .attach();

        // Creates a help command that is used to help the
        // user find a certain command.
        VelenCommand.of("help", "The starting point of the bot.", velen, new HelpCommand())
                .setUsage("help, help [command]")
                .setCategory("Utility")
                .attach();

        // Creates a simple avatar command that is used to help
        // the user grab the avatar of a user at a higher resolution.
        VelenCommand.of("avatar", "Shows a user's or your's avatar at a higher resolution.", velen, new AvatarCommand())
                .setUsage("avatar, avatar [user]")
                .addShortcuts("ava", "av")
                .setCategory("Information")
                .attach();

        // Creates a simple prefix command that is used to
        // change the prefix or get the prefix of a server.
        // This requires the permission MANAGE_SERVER.
        VelenCommand.of("prefix", "Change the prefix of the server.", velen, new PrefixCommand())
                .setUsage("prefix, prefix set [prefix]")
                .addShortcuts("pre")
                .setServerOnly(true)
                .setCategory("Utility")
                .requirePermission(PermissionType.MANAGE_SERVER)
                .attach();

        // Creates a blacklist command that can only be
        // used by the owner of the bot, it will blacklist the user
        // from using any commands of the bot.
        VelenCommand.of("blacklist", "Blacklists a user from using the bot.", velen, new BlacklistCommand())
                .setUsage("blacklist [user]")
                .setServerOnly(true)
                .setCategory("Owner")
                .addCondition(event -> event.getMessageAuthor().isBotOwner())
                .setConditionalMessage(VelenConditionalMessage.ofEmbed((user, channel, command) -> new EmbedBuilder()
                .setTitle("ERROR")
                .setDescription("This command is restricted for bot owner use only!")
                .setColor(Color.RED)))
                .attach();
    }

}

package pw.mihou.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.Velia;
import pw.mihou.velen.interfaces.VelenCommand;
import pw.mihou.velen.interfaces.VelenEvent;
import pw.mihou.velen.utils.VelenUtils;

import java.awt.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class HelpCommand implements VelenEvent {
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if(args.length > 0) {
            String command = args[0];

            Optional<VelenCommand> optCommand = Velia.velen.getCommandIgnoreCasing(command);
            // Checks if the Velen Command asked exists.
            // If it doesn't then we suggest a similar command to the user.
            if(optCommand.isPresent()) {
                message.reply(build(optCommand.get()));
            } else {
                message.reply("**ERR**: No command matches [`" + command + "`], do you possibly mean: `" + VelenUtils
                        .getCommandSuggestion(Velia.velen, command) + "`?");
            }
        } else {
            message.reply(build());
        }
    }

    /**
     * Builds a simple help embed which you can use
     * to create a very simple categorized help embed.
     *
     * @return The help embed.
     */
    private EmbedBuilder build() {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Velia - Help!")
                .setDescription("Here are all the commands that you can execute with Velia!")
                .setColor(Color.GREEN);

        builder.addInlineField("Utility", Velia.velen.getCategoryIgnoreCasing("Utility").stream().map(VelenCommand::getName)
                .map(s -> "`" + s + "`")
                .collect(Collectors.joining(", ")));

        builder.addInlineField("Information", Velia.velen.getCategoryIgnoreCasing("Information").stream().map(VelenCommand::getName)
                .map(s -> "`" + s + "`")
                .collect(Collectors.joining(", ")));
        return builder;
    }

    /**
     * Builds a simple help embed that shows the
     * details of a single command, from the cooldown to the category
     * where the command belongs.
     *
     * @param command The command to grab details of.
     * @return A detailed help embed for a specific command.
     */
    private EmbedBuilder build(VelenCommand command) {
        return new EmbedBuilder()
                .setTitle("Velia - " + command.getName())
                .setDescription(command.getDescription())
                .addField("Usage", command.getUsage())
                .addField("Shortcuts", command.getShortcuts().isEmpty() ? "No shortcuts" :
                        String.join(", ", command.getShortcuts()))
                .addField("Cooldown", command.getCooldown().toSeconds() + " seconds.")
                .addField("Supports Slash Command", command.supportsSlashCommand() ? "Yes" : "No")
                .addField("Category", command.getCategory());
    }

}

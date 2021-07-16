package pw.mihou.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;

import java.awt.*;
import java.util.Optional;

public class AvatarCommand implements VelenEvent {

    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if(args.length > 0) {
            Optional<User> optUser = event.getServer().flatMap(server -> server.getMembersByNameIgnoreCase(args[0])
                    .stream().findFirst());
            if(optUser.isPresent()) {
                optUser.ifPresent(u -> message.reply(avatarEmbed(u)));
            } else {
                message.reply("**ERR**: There is no user that has the name: [`" + args[0] + "`]");
            }
        } else {
            message.reply(avatarEmbed(user));
        }
    }

    /**
     * Creates an avatar embed that is shown to the user
     * when they search for another user's avatar or the user's
     * avatar.
     *
     * @param user The user whose avatar will be shown.
     * @return An avatar embed.
     */
    private EmbedBuilder avatarEmbed(User user) {
        return new EmbedBuilder().setTitle(user.getDiscriminatedName() + "'s Avatar")
                .setImage(user.getAvatar().getUrl().toExternalForm() + "?width=1024")
                .setColor(Color.YELLOW);
    }

}

package pw.mihou.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.Velia;
import pw.mihou.database.RedisDB;
import pw.mihou.velen.interfaces.VelenEvent;

public class BlacklistCommand implements VelenEvent {

    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if(!message.getMentionedUsers().isEmpty()) {
            message.getMentionedUsers().forEach(u -> {
                RedisDB.set("blacklist-"+u.getId(), "true");
                Velia.velen.getBlacklist().add(u.getId());
            });
            message.reply("All the users have been blacklisted from using the bot!");
        } else {
            message.reply("**ERR**: Please mention at least a single user to blacklist!");
        }
    }

}

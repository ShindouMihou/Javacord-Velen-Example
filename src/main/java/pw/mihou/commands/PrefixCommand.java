package pw.mihou.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.Velia;
import pw.mihou.velen.interfaces.VelenEvent;

public class PrefixCommand implements VelenEvent {
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        event.getServer().ifPresent(server -> {
            if(args.length > 1 && args[0].equals("set")) {
                Velia.velen.getPrefixManager().setPrefix(server.getId(), args[1]);
                message.reply("The server is now using the prefix: " + Velia.velen.getPrefixManager().getPrefix(server.getId()));
            } else {
                message.reply("The server is using the prefix: " + Velia.velen.getPrefixManager().getPrefix(server.getId()));
            }
        });
    }
}

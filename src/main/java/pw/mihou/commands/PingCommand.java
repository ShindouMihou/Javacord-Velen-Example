package pw.mihou.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;

public class PingCommand implements VelenEvent {

    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        final long startTime = System.currentTimeMillis();
        message.reply("Pong!").thenAccept(msg -> {
           final long endTime = System.currentTimeMillis();
           msg.edit("Pong was sent in " + (endTime - startTime) + " milliseconds!");
        });
    }

}

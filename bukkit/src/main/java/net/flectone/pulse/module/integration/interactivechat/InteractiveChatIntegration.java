package net.flectone.pulse.module.integration.interactivechat;
import com.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.loohp.interactivechat.InteractiveChat;
import com.loohp.interactivechat.api.InteractiveChatAPI;
import com.loohp.interactivechat.data.PlayerDataManager;
import com.loohp.interactivechat.libs.net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.loohp.interactivechat.listeners.ChatEvents;
import com.loohp.interactivechat.registry.Registry;
import lombok.RequiredArgsConstructor;
import net.flectone.pulse.annotation.Pulse;
import net.flectone.pulse.listener.PulseListener;
import net.flectone.pulse.model.entity.FEntity;
import net.flectone.pulse.model.event.Event;
import net.flectone.pulse.model.event.message.MessageFormattingEvent;
import net.flectone.pulse.module.integration.FIntegration;
import net.flectone.pulse.processing.context.MessageContext;
import net.flectone.pulse.util.constant.MessageFlag;
import net.flectone.pulse.util.logging.FLogger;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InteractiveChatIntegration implements FIntegration, PulseListener {
    private final FLogger fLogger;
    @Override
    public void hook() {
        fLogger.info("✔ Интерактивный чат подключен");
    }
    @Override
    public void unhook() {
        fLogger.info("✖ Интерактивный чат отключен");
    }
    public String checkMention(FEntity fSender, String message) {
        Player sender = Bukkit.getPlayer(fSender.getUuid());
        if (sender == null) return message;
        PlayerDataManager.PlayerData data = InteractiveChat.playerDataManager.getPlayerData(sender);
        if (InteractiveChat.allowMention && (data == null || !data.isMentionDisabled())) {
            String processedMessage;
            if (!InteractiveChat.disableEveryone && (processedMessage = ChatEvents.checkMentionEveryone("chat", message, sender)) != null) {
                return processedMessage;
            }
            if (!InteractiveChat.disableHere && (processedMessage = ChatEvents.checkMentionHere("chat", message, sender)) != null) {
                return processedMessage;
            }
            if ((processedMessage = ChatEvents.checkMentionPlayers("chat", message, sender)) != null) {
                return processedMessage;
            }
        }
        return message;
    }
    private String markSender(FEntity fSender, String message) {
        UUID sender = fSender.getUuid();
        if (Bukkit.getPlayer(fSender.getUuid()) == null) return message;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : message.split(" ")) {
            if (!Registry.MENTION_TAG_CONVERTER.containsTags(string)
                    && !string.contains("<cmd=")
                    && !string.contains("<чат=")) {
                string = InteractiveChatAPI.markSender(string, sender);
            }
            stringBuilder
                    .append(string)
                    .append(" ");
        }
        return stringBuilder.toString().trim();
    }
    public boolean sendMessage(FEntity fReceiver, Component message) {
        Player receiver = Bukkit.getPlayer(fReceiver.getUuid());
        if (receiver == null) return false;
        try {
            String serializedMessage = AdventureSerializer.serializer().gson().serialize(message);
            var deserializedMessage = GsonComponentSerializer.gson().deserialize(serializedMessage);
            InteractiveChatAPI.sendMessage(receiver, deserializedMessage);
        } catch (Exception ignored) {
            fLogger.warning("Произошла ошибка при отправке сообщений через InteractiveChat, сообщение будет отправлено через FlectonePulse.");
            return false;
        }
        return true;
    }
    @Pulse(priority = Event.Priority.LOWEST)
    public Event onMessageFormattingEvent(MessageFormattingEvent event) {
        MessageContext messageContext = event.context();
        if (!messageContext.isFlag(MessageFlag.INTERACTIVE_CHAT)) return event;
        FEntity sender = messageContext.sender();
        String message = markSender(sender, messageContext.message());
        return event.withContext(messageContext.withMessage(message));
    }
}
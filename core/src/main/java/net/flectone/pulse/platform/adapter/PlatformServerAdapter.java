package net.flectone.pulse.platform.adapter;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.google.gson.JsonElement;
import net.flectone.pulse.model.entity.FPlayer;
import net.flectone.pulse.util.constant.PlatformType;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.io.InputStream;
import java.util.UUID;
public interface PlatformServerAdapter {
    void dispatchCommand(@NonNull String command);
    @NonNull String getTPS();
    int getMaxPlayers();
    int getOnlinePlayerCount();
    int generateEntityId();
    @NonNull String getServerCore();
    @NonNull String getServerUUID();
    @NonNull PlatformType getPlatformType();
    @NonNull JsonElement getMOTD();
    boolean hasProject(@NonNull String projectName);
    boolean isOnlineMode();
    boolean isPrimaryThread();
    @NonNull String getItemName(@NonNull Object item);
    @Nullable InputStream getResource(@NonNull String path);
    void saveResource(@NonNull String path);
    @NonNull Component translateItemName(@NonNull Object item, @NonNull UUID messageUUID, boolean translatable);
    @NonNull ItemStack buildItemStack(
            @NonNull FPlayer fPlayer,
            @NonNull String material,
            @NonNull String title,
            @NonNull String lore
    );
    @NonNull ItemStack buildItemStack(
            @NonNull FPlayer fPlayer,
            @NonNull String material,
            @NonNull String title,
            @NonNull String[] lore
    );
}
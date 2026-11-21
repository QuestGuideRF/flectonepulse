package net.flectone.pulse.platform.adapter;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.potion.PotionType;
import com.github.retrooper.packetevents.protocol.world.Location;
import net.flectone.pulse.model.entity.FEntity;
import net.flectone.pulse.model.entity.FPlayer;
import net.flectone.pulse.module.message.objective.ObjectiveModule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
public interface PlatformPlayerAdapter {
    record Coordinates(double x, double y, double z) {}
    record Statistics(double health, double armor, double level, double food, double damage) {}
    record PlayedTimePlayer(String name, long playedTime) {}
    int getEntityId(@NonNull UUID uuid);
    default int getEntityId(@NonNull FEntity entity) {
        return getEntityId(entity.getUuid());
    }
    @Nullable UUID getPlayerByEntityId(int entityId);
    @Nullable UUID getUUID(@NonNull Object platformPlayer);
    @Nullable Class<?> getPlayerClass();
    @Nullable Object convertToPlatformPlayer(@NonNull UUID uuid);
    default @Nullable Object convertToPlatformPlayer(@NonNull FEntity entity) {
        return convertToPlatformPlayer(entity.getUuid());
    }
    @NonNull String getName(@NonNull UUID uuid);
    default @NonNull String getName(@NonNull FEntity entity) {
        return getName(entity.getUuid());
    }
    @NonNull String getName(@NonNull Object platformPlayer);
    @NonNull String getWorldName(@NonNull UUID uuid);
    default @NonNull String getWorldName(@NonNull FEntity entity) {
        return getWorldName(entity.getUuid());
    }
    @NonNull String getWorldEnvironment(@NonNull UUID uuid);
    default @NonNull String getWorldEnvironment(@NonNull FEntity entity) {
        return getWorldEnvironment(entity.getUuid());
    }
    @Nullable String getIp(@NonNull UUID uuid);
    default @Nullable String getIp(@NonNull FEntity entity) {
        return getIp(entity.getUuid());
    }
    @NonNull String getEntityTranslationKey(@Nullable Object platformPlayer);
    PlayerHeadObjectContents.@Nullable ProfileProperty getTexture(@NonNull UUID uuid);
    default PlayerHeadObjectContents.@Nullable ProfileProperty getTexture(@NonNull FEntity entity) {
        return getTexture(entity.getUuid());
    }
    @NonNull GameMode getGamemode(@NonNull UUID uuid);
    default @NonNull GameMode getGamemode(@NonNull FEntity entity) {
        return getGamemode(entity.getUuid());
    }
    @NonNull Component getPlayerListHeader(@NonNull FPlayer fPlayer);
    @NonNull Component getPlayerListFooter(@NonNull FPlayer fPlayer);
    int getObjectiveScore(@NonNull UUID uuid, ObjectiveModule.@Nullable Mode mode);
    default int getObjectiveScore(@NonNull FEntity entity, ObjectiveModule.@Nullable Mode mode) {
        return getObjectiveScore(entity.getUuid(), mode);
    }
    @Nullable Statistics getStatistics(@NonNull UUID uuid);
    default @Nullable Statistics getStatistics(@NonNull FEntity entity) {
        return getStatistics(entity.getUuid());
    }
    @Nullable Coordinates getCoordinates(@NonNull UUID uuid);
    default @Nullable Coordinates getCoordinates(@NonNull FEntity entity) {
        return getCoordinates(entity.getUuid());
    }
    @Nullable Location getLocation(@NonNull UUID uuid);
    default @Nullable Location getLocation(@NonNull FEntity entity) {
        return getLocation(entity.getUuid());
    }
    double distance(@NonNull UUID first, @NonNull UUID second);
    default double distance(@NonNull FEntity first, @NonNull FEntity second) {
        return distance(first.getUuid(), second.getUuid());
    }
    boolean isConsole(@NonNull Object platformPlayer);
    boolean isOperator(@NonNull UUID uuid);
    default boolean isOperator(@NonNull FEntity entity) {
        return isOperator(entity.getUuid());
    }
    boolean isSneaking(@NonNull UUID uuid);
    default boolean isSneaking(@NonNull FEntity entity) {
        return isSneaking(entity.getUuid());
    }
    boolean hasPlayedBefore(@NonNull UUID uuid);
    default boolean hasPlayedBefore(@NonNull FEntity entity) {
        return hasPlayedBefore(entity.getUuid());
    }
    boolean hasPotionEffect(@NonNull UUID uuid, @NonNull PotionType potionType);
    default boolean hasPotionEffect(@NonNull FEntity entity, @NonNull PotionType potionType) {
        return hasPotionEffect(entity.getUuid(), potionType);
    }
    boolean isOnline(@NonNull UUID uuid);
    default boolean isOnline(@NonNull FEntity entity) {
        return isOnline(entity.getUuid());
    }
    long getFirstPlayed(@NonNull UUID uuid);
    default long getFirstPlayed(@NonNull FEntity entity) {
        return getFirstPlayed(entity.getUuid());
    }
    long getLastPlayed(@NonNull UUID uuid);
    default long getLastPlayed(@NonNull FEntity entity) {
        return getLastPlayed(entity.getUuid());
    }
    long getAllTimePlayed(@NonNull UUID uuid);
    default long getAllTimePlayed(@NonNull FEntity entity) {
        return getAllTimePlayed(entity.getUuid());
    }
    void updateInventory(@NonNull UUID uuid);
    default void updateInventory(@NonNull FEntity entity) {
        updateInventory(entity.getUuid());
    }
    @Nullable Object getItem(@NonNull UUID uuid);
    default @Nullable Object getItem(@NonNull FEntity entity) {
        return getItem(entity.getUuid());
    }
    @NonNull List<UUID> getOnlinePlayers();
    @NonNull Set<UUID> findPlayersWhoCanSee(UUID uuid, double x, double y, double z);
    default @NonNull Set<UUID> findPlayersWhoCanSee(@NonNull FEntity entity, double x, double y, double z) {
        return findPlayersWhoCanSee(entity.getUuid(), x, y, z);
    }
    @NonNull List<Integer> getPassengers(UUID uuid);
    default @NonNull List<Integer> getPassengers(@NonNull FEntity entity) {
        return getPassengers(entity.getUuid());
    }
    @NonNull List<PlayedTimePlayer> getPlayedTimePlayers();
}
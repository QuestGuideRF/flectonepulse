package net.flectone.pulse;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.SneakyThrows;
import net.flectone.pulse.data.database.Database;
import net.flectone.pulse.exception.ReloadException;
import net.flectone.pulse.execution.dispatcher.EventDispatcher;
import net.flectone.pulse.execution.scheduler.TaskScheduler;
import net.flectone.pulse.model.event.Event;
import net.flectone.pulse.model.event.lifecycle.DisableEvent;
import net.flectone.pulse.model.event.lifecycle.EnableEvent;
import net.flectone.pulse.model.event.lifecycle.ReloadEvent;
import net.flectone.pulse.model.event.player.PlayerLoadEvent;
import net.flectone.pulse.platform.controller.DialogController;
import net.flectone.pulse.platform.controller.InventoryController;
import net.flectone.pulse.platform.controller.ModuleController;
import net.flectone.pulse.platform.registry.*;
import net.flectone.pulse.platform.render.TextScreenRender;
import net.flectone.pulse.service.FPlayerService;
import net.flectone.pulse.service.MetricsService;
import net.flectone.pulse.service.MinecraftTranslationService;
import net.flectone.pulse.service.ModerationService;
import net.flectone.pulse.util.file.FileFacade;
import net.flectone.pulse.util.logging.FLogger;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
@Singleton
public class FlectonePulseAPI  {
    @Getter
    private static FlectonePulse instance;
    @Inject
    public FlectonePulseAPI(FlectonePulse instance) {
        FlectonePulseAPI.instance = instance;
    }
    public static void configurePacketEvents() {
        System.setProperty("packetevents.nbt.default-max-size", "2097152");
    }
    public static void terminateFailedPacketEvents() {
        try {
            PacketEventsAPI<?> packetEventsAPI = PacketEvents.getAPI();
            if (!packetEventsAPI.isInitialized()) {
                packetEventsAPI.getInjector().uninject();
            }
        } catch (Exception ignored) {
        }
    }
    @SneakyThrows
    public void onEnable() {
        if (!instance.isReady()) return;
        FileFacade fileFacade = instance.get(FileFacade.class);
        fileFacade.reload();
        instance.get(CacheRegistry.class).init();
        FLogger fLogger = instance.get(FLogger.class);
        fLogger.setFileFacade(fileFacade);
        fLogger.logDescription();
        instance.get(MinecraftTranslationService.class).reload();
        instance.get(ListenerRegistry.class).registerDefaultListeners();
        fLogger.setupFilter();
        instance.get(Database.class).connect();
        PacketEvents.getAPI().init();
        instance.get(ModuleController.class).reload();
        instance.get(FPlayerService.class).reload();
        instance.get(ProxyRegistry.class).onEnable();
        if (fileFacade.config().metrics().enable()) {
            instance.get(MetricsService.class).reload();
        }
        instance.get(EventDispatcher.class).dispatch(new EnableEvent(instance));
        fLogger.logEnabled();
    }
    public void onDisable() {
        terminateFailedPacketEvents();
        if (!instance.isReady()) return;
        FLogger fLogger = instance.get(FLogger.class);
        fLogger.logDisabling();
        instance.get(EventDispatcher.class).dispatch(new DisableEvent(instance));
        instance.get(TaskScheduler.class).shutdown();
        instance.get(InventoryController.class).closeAll();
        instance.get(DialogController.class).closeAll();
        FPlayerService fPlayerService = instance.get(FPlayerService.class);
        fPlayerService.getOnlineFPlayers().forEach(fPlayer -> {
            fPlayer.setOnline(false);
            fPlayerService.updateFPlayer(fPlayer);
        });
        fPlayerService.clear();
        instance.get(ModuleController.class).terminate();
        instance.get(ListenerRegistry.class).unregisterAll();
        PacketEvents.getAPI().terminate();
        instance.get(ProxyRegistry.class).onDisable();
        instance.get(Database.class).disconnect();
        fLogger.logDisabled();
    }
    public void reload() throws ReloadException {
        if (!instance.isReady()) return;
        ReloadException reloadException = null;
        FLogger fLogger = instance.get(FLogger.class);
        fLogger.logReloading();
        instance.get(InventoryController.class).closeAll();
        instance.get(DialogController.class).closeAll();
        ListenerRegistry listenerRegistry = instance.get(ListenerRegistry.class);
        Map<Event.Priority, List<UnaryOperator<Event>>> reloadListeners = listenerRegistry.getPulseListeners(ReloadEvent.class);
        listenerRegistry.reload();
        instance.get(TaskScheduler.class).reload();
        FileFacade fileFacade = instance.get(FileFacade.class);
        Database database = instance.get(Database.class);
        Database.Type oldDatabaseType = database.config().type();
        try {
            fileFacade.reload();
        } catch (Exception e) {
            reloadException = new ReloadException(e);
        }
        instance.get(MinecraftTranslationService.class).reload();
        instance.get(CommandRegistry.class).reload();
        instance.get(PermissionRegistry.class).reload();
        instance.get(ProxyRegistry.class).reload();
        fLogger.setupFilter();
        database.disconnect();
        try {
            database.connect();
        } catch (Exception e) {
            if (reloadException == null) {
                reloadException = new ReloadException(e);
            }
            if (database.config().type() != oldDatabaseType) {
                fileFacade.updateFilePack(filePack ->
                        filePack.withConfig(
                                filePack.config().withDatabase(
                                        filePack.config().database().withType(oldDatabaseType)
                                )
                        )
                );
                try {
                    database.connect();
                } catch (Exception ignored) {
                    throw reloadException;
                }
            }
        }
        FPlayerService fPlayerService = instance.get(FPlayerService.class);
        fPlayerService.reload();
        instance.get(ModerationService.class).reload();
        instance.get(ModuleController.class).reload();
        instance.get(TextScreenRender.class).clear();
        EventDispatcher eventDispatcher = instance.get(EventDispatcher.class);
        fPlayerService.getPlatformFPlayers().forEach(fPlayer ->
                eventDispatcher.dispatch(new PlayerLoadEvent(fPlayer, true))
        );
        if (fileFacade.config().metrics().enable()) {
            instance.get(MetricsService.class).reload();
        }
        eventDispatcher.dispatch(reloadListeners, new ReloadEvent(instance, reloadException));
        fLogger.logReloaded();
        if (reloadException != null) {
            throw reloadException;
        }
    }
}
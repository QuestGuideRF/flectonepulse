package net.flectone.pulse;
import com.github.retrooper.packetevents.PacketEvents;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.flectone.pulse.exception.ReloadException;
import net.flectone.pulse.processing.resolver.BukkitLibraryResolver;
import net.flectone.pulse.processing.resolver.LibraryResolver;
import net.flectone.pulse.util.logging.FLogger;
import org.bukkit.plugin.java.JavaPlugin;
@Getter
@Singleton
public class BukkitFlectonePulse extends JavaPlugin implements FlectonePulse {
    private FLogger fLogger;
    private LibraryResolver libraryResolver;
    private Injector injector;
    @Override
    public void onLoad() {
        fLogger = new FLogger(this.getLogger());
        fLogger.logEnabling();
        libraryResolver = new BukkitLibraryResolver(this, fLogger);
        libraryResolver.addLibraries();
        libraryResolver.resolveRepositories();
        libraryResolver.loadLibraries();
        FlectonePulseAPI.configurePacketEvents();
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false).checkForUpdates(false).debug(false);
        injector = Guice.createInjector(new BukkitInjector(this, this, libraryResolver, fLogger));
        PacketEvents.getAPI().load();
    }
    @Override
    public <T> T get(Class<T> type) {
        if (injector == null) {
            throw new IllegalStateException("FlectonePulse еще не инициализирован");
        }
        return injector.getInstance(type);
    }
    @Override
    public boolean isReady() {
        return injector != null;
    }
    @Override
    public void onEnable() {
        if (!isReady()) return;
        injector.getInstance(FlectonePulseAPI.class).onEnable();
    }
    @Override
    public void onDisable() {
        if (!isReady()) {
            FlectonePulseAPI.terminateFailedPacketEvents();
            return;
        }
        injector.getInstance(FlectonePulseAPI.class).onDisable();
    }
    @Override
    public void reload() throws ReloadException {
        if (!isReady()) return;
        injector.getInstance(FlectonePulseAPI.class).reload();
    }
}
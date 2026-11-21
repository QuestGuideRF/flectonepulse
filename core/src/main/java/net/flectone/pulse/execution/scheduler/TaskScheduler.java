package net.flectone.pulse.execution.scheduler;
import net.flectone.pulse.model.entity.FPlayer;
import java.util.function.Consumer;
public interface TaskScheduler {
    void shutdown();
    void reload();
    void runAsync(SchedulerRunnable runnable, boolean independent);
    void runAsyncLater(SchedulerRunnable runnable, long delay);
    void runAsyncTimer(SchedulerRunnable runnable, long delay, long period);
    void runSync(SchedulerRunnable runnable);
    void runSyncLater(SchedulerRunnable runnable, long delay);
    void runSyncTimer(SchedulerRunnable runnable, long delay, long period);
    void runRegion(FPlayer fPlayer, SchedulerRunnable runnable, boolean sync);
    void runRegionLater(FPlayer fPlayer, SchedulerRunnable runnable, long delay);
    void runRegionTimer(FPlayer fPlayer, SchedulerRunnable runnable, long delay, long period);
    void runPlayerRegionTimer(Consumer<FPlayer> fPlayerConsumer, long delay);
    Runnable wrapExceptionRunnable(SchedulerRunnable runnable);
    default void runRegion(FPlayer fPlayer, SchedulerRunnable runnable) {
        runRegion(fPlayer, runnable, false);
    }
    default void runAsync(SchedulerRunnable runnable) {
        runAsync(runnable, false);
    }
    default void runAsyncLater(SchedulerRunnable runnable) {
        runAsyncLater(runnable, 20L);
    }
    default void runAsyncTimer(SchedulerRunnable runnable, long period) {
        runAsyncTimer(runnable, 0L, period);
    }
    default void runSyncTimer(SchedulerRunnable runnable, long period) {
        runSyncTimer(runnable, 0L, period);
    }
    default void runRegionTimer(FPlayer fPlayer, SchedulerRunnable runnable, long period) {
        runRegionTimer(fPlayer, runnable, 0L, period);
    }
    default boolean isRestrictedAsyncThread() {
        String threadName = Thread.currentThread().getName();
        return threadName.startsWith("Netty") || threadName.startsWith("Асинхронный чат");
    }
}
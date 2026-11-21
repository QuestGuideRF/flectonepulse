package net.flectone.pulse.util;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.ThreadLocalRandom;
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RandomUtil {
    public int nextInt(int start, int end) {
        if (start > end) return 0;
        return start == end ? start : start + ThreadLocalRandom.current().nextInt(end - start);
    }
    public int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
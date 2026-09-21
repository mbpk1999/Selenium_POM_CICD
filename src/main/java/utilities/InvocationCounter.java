package utilities;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InvocationCounter {
    private static final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    public static int next(String methodName) {
        return counters.computeIfAbsent(methodName, k -> new AtomicInteger(0))
                .incrementAndGet();
    }
}

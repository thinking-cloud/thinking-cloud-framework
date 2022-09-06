package tcf.utils.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池 - 创建线程的工厂类
 */
public class ThinkingCloudThreadFactory  implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;


    ThinkingCloudThreadFactory(String groupName, String threadName) {
        this(new ThreadGroup(groupName),threadName);
    }

    ThinkingCloudThreadFactory(ThreadGroup group, String threadName) {
        this.group = group;
        namePrefix = threadName+"-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}

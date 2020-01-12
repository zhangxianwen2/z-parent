package com.seven.bootstarter.logger.executor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * 重写线程池，使子线程继承父线程的MDC
 *
 * @author zhangxianwen
 * 2020/1/12 12:27
 **/
@Slf4j
public class MDCThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable runnable) {
        log.debug("MDC将被传递到子线程:{}", MDC.getCopyOfContextMap());
        // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
        Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(() -> run(runnable, context));
    }

    /**
     * 子线程委托的执行方法
     *
     * @param runnable {@link Runnable}
     * @param context  父线程MDC内容
     */
    private void run(Runnable runnable, Map<String, String> context) {
        // 将父线程的MDC内容传给子线程
        if (context != null) {
            MDC.setContextMap(context);
        }
        try {
            // 执行异步操作
            runnable.run();
        } finally {
            // 清空MDC内容
            MDC.clear();
        }
    }
}

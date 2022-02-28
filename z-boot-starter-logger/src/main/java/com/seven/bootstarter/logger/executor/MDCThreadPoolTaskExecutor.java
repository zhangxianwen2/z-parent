package com.seven.bootstarter.logger.executor;

import com.seven.bootstarter.logger.filter.ZMDC;
import lombok.extern.slf4j.Slf4j;
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
        log.debug("MDC将被传递到子线程:{}", ZMDC.getCopyOfContextMap());
        // 获取父线程MDC中的内容必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
        Map<String, String> context = ZMDC.getCopyOfContextMap();
        super.execute(() -> run(runnable, context));
    }

    /**
     * 子线程委托的执行方法
     *
     * @param runnable {@link Runnable}
     * @param context  父线程MDC内容
     */
    private void run(Runnable runnable, Map<String, String> context) {
        if (context != null) {
            ZMDC.setContextMap(context);
        }
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("子线程执行异常：", e);
        } finally {
            // 清空MDC内容
            ZMDC.clear();
        }
    }

}

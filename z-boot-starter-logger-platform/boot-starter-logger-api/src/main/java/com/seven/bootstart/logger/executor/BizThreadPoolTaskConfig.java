package com.seven.bootstart.logger.executor;

import com.seven.bootstarter.logger.executor.MDCThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author zhangxianwen
 * 2020/1/12 12:27
 **/
@EnableAsync
@Configuration
public class BizThreadPoolTaskConfig {
    private static final int CORE_POOL_SIZE = 10;   // 核心线程数（默认线程数）
    private static final int MAX_POOL_SIZE = 100;   // 最大线程数
    private static final int KEEP_ALIVE_TIME = 10;  // 允许线程空闲时间（单位：默认为秒）
    private static final int QUEUE_CAPACITY = 200;  // 缓冲队列数
    private static final String THREAD_NAME_PREFIX = "Biz-Async-Service-";  // 线程池名前缀

    /**
     * \@Async 不指定线程池时默认使用这个(默认使用第一个)
     * 非特殊指定时使用的线程池
     */
    @Bean(name = "bizTaskExecutor")
    public TaskExecutor bizTaskExecutor() {
        MDCThreadPoolTaskExecutor executor = new MDCThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return new ConcurrentTaskExecutor(executor);
    }
}

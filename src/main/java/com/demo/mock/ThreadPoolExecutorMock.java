/*
 * All rights Reserved, Designed By baowei
 *
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.demo.mock;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yan.zhang
 * @date 2020/10/14 22:55
 */
public class ThreadPoolExecutorMock {
    public static ThreadPoolExecutor mock() {

        return new ThreadPoolExecutor(5, 10,
                60L,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                int index = atomicInteger.getAndIncrement();
                System.out.println("ThreadPoolExecutorMock: create no " + index + " thread");

                Thread thread = new Thread(r, "mock-thread-" + index);
                //设置线程池的创建的线程为守护线程，main退出 程序结束
                thread.setDaemon(true);


                //如果这里设置为false,代表线程池创建的线程为非守护线程，main线程退出，非守护线程不会结束，导致jvm进程不会退出
                //thread.setDaemon(false);
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

    }
}

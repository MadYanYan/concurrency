package com.demo.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author yan.zhang
 * @Date 2021/4/8 18:33
 * @Version 1.0
 */
public class ExceptionByExecute {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(1 / 0);
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("thread pool execute another task!");
        });
    }

}

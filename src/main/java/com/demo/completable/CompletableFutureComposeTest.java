package com.demo.completable;

import com.demo.mock.ThreadPoolExecutorMock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yan.zhang
 * @Date 2021/12/1 16:53
 * @Version 1.0
 */
public class CompletableFutureComposeTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = ThreadPoolExecutorMock.mock();

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1...start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {

            }
            System.out.println("compute 1...end");
            return 1;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 2..start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {

            }
            System.out.println("compute 2..end");
            return 2;
        });

        future1.thenCompose((result) -> CompletableFuture.supplyAsync(() -> result + 10)).join();
    }
}

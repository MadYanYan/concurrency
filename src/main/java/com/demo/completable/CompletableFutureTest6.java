/*
 * All rights Reserved, Designed By baowei
 *
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.demo.completable;

import com.demo.mock.BigCollectionMock;
import com.demo.mock.ThreadPoolExecutorMock;
import com.demo.utils.ListWrapUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author yan.zhang
 * @date 2020/10/14 23:23
 */
public class CompletableFutureTest6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = ThreadPoolExecutorMock.mock();
        List<Integer> list = BigCollectionMock.mock();

        List<List<Integer>> split = ListWrapUtils.split(list, 200);


        CompletableFuture<String> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync()..." + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (List<Integer> integers : split) {
                for (Integer integer : integers) {

                }
            }
        }, executor).thenAcceptAsync(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                System.out.println("thenAccept()..." + Thread.currentThread().getName());
            }
        },executor).thenApplyAsync(result -> {
            System.out.println("thenApplyAsync()..." + Thread.currentThread().getName());
            return result + " Processed Result";
        }, executor);


        String result = completableFuture.get();

        System.out.println("main-thread");

        System.out.println(result);
    }
}

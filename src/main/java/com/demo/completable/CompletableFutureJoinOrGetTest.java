package com.demo.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author yan.zhang
 * @Date 2021/12/1 17:00
 * @Version 1.0
 */
public class CompletableFutureJoinOrGetTest {
    public static void main(String[] args) {
        /**
         * join()和get()方法都是阻塞调用它们的线程,来获取CompletableFuture异步之后的返回值。
         */
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
          return 1/0;
        });
        //join抛出未受检查异常
        //java.util.concurrent.CompletionException
        CompletableFuture.allOf(f1).join();
        System.out.println("CompletableFuture Test");


        try {
            //get()抛出受检查异常，ExecutionException, InterruptedException 需要用户手动处理
            CompletableFuture.allOf(f1).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}

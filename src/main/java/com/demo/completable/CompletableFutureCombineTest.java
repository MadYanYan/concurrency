package com.demo.completable;

import com.demo.mock.ThreadPoolExecutorMock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yan.zhang
 * @Date 2021/12/1 16:47
 * @Version 1.0
 */
public class CompletableFutureCombineTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        /**
         * thenCombine()
         * 而允许前后连接的两个任务可以并行执行（后置任务不需要等待前置任务执行完成）
         * 最后当两个任务均完成时，再将其结果同时传递给下游处理任务，从而得到最终结果。
         * 适用于没有前后依赖关系之间的任务进行连接
         */
        System.out.println("thenCombine...start");
        future1.thenCombine(future2, Integer::sum).get();
        System.out.println("thenCombine...end");
    }
}

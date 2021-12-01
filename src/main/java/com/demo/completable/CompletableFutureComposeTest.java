package com.demo.completable;

import com.demo.mock.ThreadPoolExecutorMock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

/**
 * @Author yan.zhang
 * @Date 2021/12/1 16:53
 * @Version 1.0
 */
public class CompletableFutureComposeTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = ThreadPoolExecutorMock.mock();

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        });

        //如果任务之间有前后依赖关系，但是连接是独立的CompletableFuture
        CompletableFuture<CompletableFuture<Integer>> future2 = future1.thenApply((result) -> CompletableFuture.supplyAsync(() -> result + 10));

        //此时获取计算结果
        //当连接任务越来越多，代码会越来越复杂，引入thenCompose，类比flatMap
        System.out.println(future2.join().join());


        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 3");
            return 1;
        });

        CompletableFuture<Integer> future4 = future3.thenCompose((r) -> CompletableFuture.supplyAsync(() -> r + 10));
        System.out.println(future4.join());


        /**
         * <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn)
         * <U> CompletionStage<U> thenCompose(Function<? super T,? extends CompletionStage<U>> fn)
         *
         * 对于thenApply，fn函数是一个对一个已完成的stage或者说CompletableFuture的的返回值进行计算、操作
         * 对于thenCompose，fn函数是对另一个CompletableFuture进行计算、操作。
         */

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> 100).thenApply(new Function<Integer, String>() {
            @Override
            public String apply(Integer num) {
                return num + " to String";
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> 100).thenCompose(new Function<Integer, CompletionStage<String>>() {
            @Override
            public CompletionStage<String> apply(Integer integer) {
                return CompletableFuture.supplyAsync(() -> integer + " to String");
            }
        });

        System.out.println(f1.join());
        System.out.println(f2.join());
    }
}

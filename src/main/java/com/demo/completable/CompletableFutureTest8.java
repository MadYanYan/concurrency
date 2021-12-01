package com.demo.completable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author yan.zhang
 * @Date 2021/12/1 15:02
 * @Version 1.0
 */
public class CompletableFutureTest8 {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync()..." + Thread.currentThread().getName());
            return 1 / 0;
        }).thenApplyAsync(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                System.out.println("thenApply()..." + Thread.currentThread().getName());
                return input * 10;
            }
        }).handleAsync((BiFunction<Integer, Throwable, Integer>) (integer, throwable) -> {
            System.out.println("handleAsync()..." + Thread.currentThread().getName());
            if (Objects.isNull(throwable)) {
                /**
                 * handle()和whenComplete()区别在于，handle接收上一阶段返回值，并且影响最终返回结果。
                 * 这里返回10，如果是whenComplete()，没有返回值，future.get()是上一阶段的返回值。
                 */
                return 10;
            } else {
                /**
                 * 如果是whenComplete(),future.get()会抛出异常
                 */
                System.out.println(throwable.getMessage());
                return 10 + 20;
            }
        });


        try {
            System.out.println("线程：" + Thread.currentThread().getName() + " 结果：" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

package com.demo.common.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author yan.zhang
 * @date 2022/5/4 14:26
 */
public class FizzBuzz {
    //循环边界值
    private int n;
    //当前值
    private int curr = 1;
    private static Lock lc = new ReentrantLock();
    Condition c = lc.newCondition();

    /**
     * 判断是否整除
     * 交替打印字符串
     * https://leetcode-cn.com/problems/fizz-buzz-multithreaded/
     */
    public static void main(String[] args) throws InterruptedException {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        Runnable fizz = () -> {
            try {
                fizzBuzz.fizz(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("fizz");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable buzz = () -> {
            try {
                fizzBuzz.buzz(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("buzz");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(fizz).start();
        new Thread(fizz).start();
        new Thread(fizz).start();
        new Thread(fizz).start();

        Thread.sleep(10);
    }


    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        lc.lock();
        try {
            while (curr <= n) {
                if (curr % 3 == 0 && curr % 5 != 0) {
                    printFizz.run();
                    curr++;
                    c.signalAll();
                } else {
                    c.await();
                }
            }
        } finally {
            lc.unlock();
        }
    }

    // 仅能被5整除 printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        lc.lock();
        try {
            while (curr <= n) {
                if (curr % 3 != 0 && curr % 5 == 0) {
                    printBuzz.run();
                    curr++;
                    //唤醒所有等待的线程
                    c.signalAll();
                } else {
                    c.await();
                }
            }
        } finally {
            lc.unlock();
        }
    }

    /**
     * 如果同时被3和5整除
     * printFizzBuzz.run() outputs "fizzbuzz".
     *
     * @param printFizzBuzz
     * @throws InterruptedException
     */
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        lc.lock();
        try {
            while (curr <= n) {
                if (curr % 3 == 0 && curr % 5 == 0) {
                    printFizzBuzz.run();
                    curr++;
                    //唤醒所有等待的线程
                    c.signalAll();
                } else {
                    c.await();
                }
            }
        } finally {
            lc.unlock();
        }
    }

    /**
     * 输出既不能被 3 整除也不能被 5 整除的数字。
     * printNumber.accept(x) outputs "x", where x is an integer.
     *
     * @param printNumber
     * @throws InterruptedException
     */
    public void number(IntConsumer printNumber) throws InterruptedException {
        lc.lock();
        try {
            while (curr <= n) {
                if (curr % 3 != 0 && curr % 5 != 0) {
                    printNumber.accept(curr);
                    curr++;
                    //唤醒所有等待的线程
                    c.signalAll();
                } else {
                    c.await();
                }
            }
        } finally {
            lc.unlock();
        }
    }
}

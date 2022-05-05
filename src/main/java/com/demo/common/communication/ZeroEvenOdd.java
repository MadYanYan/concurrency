package com.demo.common.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author yan.zhang
 * @date 2022/5/4 18:14
 */
public class ZeroEvenOdd {
    /**
     * https://leetcode-cn.com/problems/print-zero-even-odd/solution/java-san-chong-xing-neng-you-yue-de-jie-h4pxx/
     * 打印零与奇偶数
     * zero线程先打印，奇数线程打印，zero线程打印，偶数线程打印，奇数线程打印....如此往复
     */
    private int n;
    private int curr = 0;
    private Lock lc = new ReentrantLock();
    private Condition z = lc.newCondition();
    private Condition e = lc.newCondition();
    private Condition o = lc.newCondition();


    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lc.lock();
        try {
            //注意这里从i = 1开始
            for (int i = 1; i <= n; i++) {
                if (curr != 0) {
                    z.await();
                }
                printNumber.accept(0);
                //唤醒其他线程
                if (i % 2 == 0) {
                    curr = 2;
                    e.signal();
                } else {
                    curr = 1;
                    o.signal();
                }
            }
        } finally {
            lc.unlock();
        }
    }

    //只输出偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        lc.lock();
        try {
            for (int i = 2; i <= n; i += 2) {
                if (curr != 2) {
                    e.await();
                }
                printNumber.accept(i);
                curr = 0;
                z.signal();
            }
        } finally {
            lc.unlock();
        }
    }

    //只输出奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        lc.lock();
        try {
            for (int i = 1; i <= n; i += 2) {
                if (curr != 1) {
                    o.await();
                }
                printNumber.accept(i);
                curr = 0;
                z.signal();
            }
        } finally {
            lc.unlock();
        }
    }
}

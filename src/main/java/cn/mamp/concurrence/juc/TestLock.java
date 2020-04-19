package cn.mamp.concurrence.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用户解决多线程安全问题的方式
 * 1. 隐式锁 synchronized:
 * 1.1 同步代码块
 * 2.2 同步方法
 * 2. 显示锁 Lock jdk 1.5 后
 * 注意: 显式锁,需要通过lock()方法上锁,必须通过unlock()方法进行释放锁
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 0; i < 3; i++) {
            new Thread(ticket, (i + 1) + "号窗口").start();
        }
    }
}

class Ticket implements Runnable {
    private int total = 10000;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {
            // 上锁
            lock.lock();
            try {
                if (total > 0) {
                    TimeUnit.MICROSECONDS.sleep(200);
                    System.out.println(Thread.currentThread().getName() + "完成售票,余票::" + --total);
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放锁 ,unlock() 操作必须放在 finally的第一行
                lock.unlock();
            }
        }
    }
}

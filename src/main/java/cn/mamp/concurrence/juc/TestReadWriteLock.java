package cn.mamp.concurrence.juc;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock : 两个锁: 1. 读锁-多个读线程同时访问 2. 写锁-只能一个写线程访问
 *
 * @author mamp
 * @data 2020/5/6
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        // 一个写线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write();
            }
        }, "write_thread").start();

        // 10个读线程
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.read();
                }
            }, "read_thread_" + (i + 1)).start();
        }

    }
}

class ReadWriteLockDemo {
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private int num = 0;

    /**
     * 读
     */
    public void read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " read : " + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 写
     */
    public void write() {
        lock.writeLock().lock();
        try {
            int radom = new Random().nextInt();
            num = radom;
            System.out.println(Thread.currentThread().getName() + " write : " + radom);
        } finally {
            lock.writeLock().unlock();
        }
        System.out.println();
    }
}
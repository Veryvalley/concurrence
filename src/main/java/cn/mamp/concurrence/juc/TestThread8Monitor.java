package cn.mamp.concurrence.juc;

import java.util.concurrent.TimeUnit;

/**
 * 线程同步的8种情况: 线程的锁在某个具体的对象上, 如果多个线程共同抢同一个锁, 同一时刻只能有一个线程拥有锁,即使调用不同的方法, 静态同步方法的同步锁在
 * 类对象,即 ClassName.class ,非静态同步方法的同步锁对象是 类的当前实例,即 this
 * 1.
 *
 * @author mamp
 * @data 2020/5/6
 */
public class TestThread8Monitor {
    public static void main(String[] args) {
        Thread8MonitorDemo demo = new Thread8MonitorDemo();
        Thread8MonitorDemo demo1 = new Thread8MonitorDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.one();
            }
        }, "one").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.tow();
            }
        }, "tow").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.three();
            }
        }, "tow").start();
    }
}

class Thread8MonitorDemo {

    public static synchronized void one() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void tow() {
        System.out.println("tow");
    }

    public void three() {
        System.out.println("three");
    }
}

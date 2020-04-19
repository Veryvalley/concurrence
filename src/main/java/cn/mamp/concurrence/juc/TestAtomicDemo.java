package cn.mamp.concurrence.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * i++ 的原子性问题
 * int i = 10;
 * i = i++;
 * i++操作实际分三步骤:
 * int temp = i;  // 读
 * i = i+1;       // 改
 * i = temp;      // 写
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        // 非原子操作
        // NoneAtomicDemo demo = new NoneAtomicDemo();
        // 原子操作
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo, "线程" + (i + 1)).start();
        }
    }
}

/**
 * 非原子操作
 */
class NoneAtomicDemo implements Runnable {
    int serial = 0;

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 获取的serial NO为: " + getSerial());
    }

    private int getSerial() {
        return serial++;
    }
}

/**
 * 原子操作
 */
class AtomicDemo implements Runnable {
    AtomicInteger serial = new AtomicInteger();

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 获取的serial NO为: " + getSerial());
    }

    private int getSerial() {
        return serial.getAndAdd(1);
    }
}
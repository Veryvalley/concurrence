package cn.mamp.concurrence.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. i++ 的原子性问题
 *      int i = 10;
 *      i = i++;
 *      i++操作实际分三步骤:
 *      int temp = i;  // 读
 *      i = i+1;       // 改
 *      i = temp;      // 写
 * 2. 原子变量: jdk 1.5后,java.util.concurrent.atomic 包下提供了常用的原子变量:
 *      a. volatile 保证内存的可见性
 *      b. CAS (compare-and-swap) 算法 保证数据的原子性
 *      CAS算法是 硬件对于并发操作共享数据的支持
 *      CAS算法包含了三个操作数:
 *      内存值 V
 *      预估值 A
 *      更新值 B
 *      当且仅当 V == B  时,  V = B, 否则不做作任何操作
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
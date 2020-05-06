package cn.mamp.concurrence.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 需求: 有A,B,C在三个线程, 按A -> B -> C 的顺序打印打印线程名称, 循环5次
 *
 * @author mamp
 * @data 2020/5/6
 */
public class TestAlternateThread {
    public static void main(String[] args) {
        AlternateDemo demo = new AlternateDemo();
        // 线程A
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    demo.loopA(i);
                }
            }
        }, "A").start();
        // 线程B
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    demo.loopB(i);
                }
            }
        }, "B").start();
        // 线程C
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    demo.loopC(i);
                }
            }
        }, "C").start();
    }
}

class AlternateDemo {
    // 当前loop的标记,1-> loopA, 2-> loopB,3-> loopC
    private int currentFlag = 1;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    /**
     * 打印A
     *
     * @param loopNum 第几次打印
     */
    public void loopA(int loopNum) {
        lock.lock();
        try {
            // 1. 判断
            if (currentFlag != 1) {
                conditionA.await();
            }
            // 2. 打印 A
            System.out.println(Thread.currentThread().getName() + ":" + loopNum);
            // 3. 唤醒 B
            currentFlag = 2;
            conditionB.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 打印B
     *
     * @param loopNum 第几次打印
     */
    public void loopB(int loopNum) {
        lock.lock();
        try {
            // 1. 判断
            if (currentFlag != 2) {
                conditionB.await();
            }
            // 2. 打印 B
            System.out.println(Thread.currentThread().getName() + ":" + loopNum);
            // 3. 唤醒 C
            currentFlag = 3;
            conditionC.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印C
     *
     * @param loopNum 第几次打印
     */
    public void loopC(int loopNum) {
        lock.lock();
        try {
            // 1. 判断
            if (currentFlag != 3) {
                conditionC.await();
            }
            // 2. 打印 C
            System.out.println(Thread.currentThread().getName() + ":" + loopNum);
            // 3. 唤醒 A
            currentFlag = 1;
            conditionA.signalAll();
            System.out.println("-----------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

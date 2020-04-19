package cn.mamp.concurrence.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        LatchDemo demo = new LatchDemo(latch);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(demo).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
        long end = System.currentTimeMillis();

        System.out.println("耗时:" + (end - start));
    }
}

class LatchDemo implements Runnable {
    CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
        latch.countDown();
    }
}

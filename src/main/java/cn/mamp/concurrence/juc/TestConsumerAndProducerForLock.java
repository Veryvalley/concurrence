package cn.mamp.concurrence.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mamp
 * @data 2020/5/6
 */
public class TestConsumerAndProducerForLock {
    public static void main(String[] args) {
        ClerkForLock clerk = new ClerkForLock();
        ProductorForLock productor = new ProductorForLock(clerk);
        ConsumerForLock consumer = new ConsumerForLock(clerk);

        for (int i = 0; i < 3; i++) {
            new Thread(productor, "[lock]生产者" + (i + 1)).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(consumer, "[lock]消费者" + (i + 1)).start();
        }
    }
}

/**
 * 柜台售货员
 */
class ClerkForLock {
    /**
     * 当前存货数量, 假设货架数量为10,超过10生产者不能再给clerk 送货
     */
    private int size = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 进货
     */
    public void get() {
        lock.lock();
        try {
            // ****** wait 存在虚假唤醒问题,必须用在循环内 *****
            while (size >= 10) {
                System.out.println("货架已满,无法进货");
                //return;  // 这里不能return,可能会导致线程无法唤醒
                try {
                    // wait 存在虚假唤醒问题,必须用在循环内
                    // condition.await() 与 this.wait()的作用一样
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            size++;
            System.out.println(Thread.currentThread().getName() + "进货成功,当前存货数量:" + size);
            //  condition.signalAll() 与this.notifyAll()的作用一样
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 售货
     */
    public void sale() {
        lock.lock();
        try {
            while (size <= 0) {
                System.out.println("货架已空,无货");
                //return;  // 这里不能return,可能会导致线程无法唤醒
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            size--;
            System.out.println(Thread.currentThread().getName() + "售货成功,当前存货数量:" + size);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 生产者类
 */
class ProductorForLock implements Runnable {

    private ClerkForLock clerk;

    public ProductorForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

/**
 * 消费者类
 */
class ConsumerForLock implements Runnable {

    private ClerkForLock clerk;

    public ConsumerForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}




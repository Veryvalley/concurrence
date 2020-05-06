package cn.mamp.concurrence.juc;

/**
 * @author mamp
 * @data 2020/5/6
 */
public class TestConsumerAndProducer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        for (int i = 0; i < 3; i++) {
            new Thread(productor, "生产者" + (i + 1)).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(consumer, "消费者" + (i + 1)).start();
        }
    }
}

/**
 * 柜台售货员
 */
class Clerk {
    /**
     * 当前存货数量, 假设货架数量为10,超过10生产者不能再给clerk 送货
     */
    private int size = 0;

    /**
     * 进货
     */
    public synchronized void get() {
        // ****** wait 存在虚假唤醒问题,必须用在循环内 *****
        while (size >= 10) {
            System.out.println("货架已满,无法进货");
            //return;  // 这里不能return,可能会导致线程无法唤醒
            try {
                // wait 存在虚假唤醒问题,必须用在循环内
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size++;
        System.out.println(Thread.currentThread().getName() + "进货成功,当前存货数量:" + size);
        this.notifyAll();
    }

    /**
     * 售货
     */
    public synchronized void sale() {
        while (size <= 0) {
            System.out.println("货架已空,无货");
            //return;  // 这里不能return,可能会导致线程无法唤醒
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size--;
        System.out.println(Thread.currentThread().getName() + "售货成功,当前存货数量:" + size);
        this.notifyAll();
    }
}

/**
 * 生产者类
 */
class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
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
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}

package cn.mamp.concurrence.thread;

/**
 * 需求: 两个线程交替打印数字:
 * 知识点: Object 的 wait() ,notify(), 和 notityAll()中,
 */
public class WaitAndNotity {
    public static void main(String[] args) {
        Work w = new Work();
        Thread t1 = new Thread(w);
        t1.setName("t1");
        Thread t2 = new Thread(w);
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}

/**
 * Work 线程类
 */
class Work implements Runnable {
    private Object obj = new Object();
    private static int num = 0;

    @Override
    public void run() {
        while (true) {
      /*      synchronized (this) {
                // synchronized (obj) {
                //  1. wait 和 notify 必须在两步代码块的范围内调用,否则会报 Java.lang.IllegalMonitorStateException
                //  2. 必须调用 "同步" 对象的 wait和notify,notifyAll方法,  实例同步方法-this, 静态同步方法- Class.class , 同步块-  synchronized (obj) 中的obj
                notify();
                // obj.notify();
                if (num < 200) {
                    System.out.println(Thread.currentThread().getName() + "::" + num++);
                } else {
                    break;
                }
                try {
                    // wait 和 notify 必须在同代码库的范围内调用
                    wait();
                    // obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            // boolean result = add();
            // boolean result = add1();
            boolean result = add2();
            if (!result) {
                System.out.println("结束");
                break;
            }
        }
    }

    /**
     * 同步块
     *
     * @return
     */
    public boolean add2() {
        synchronized (obj) {
            //  1. wait 和 notify 必须在两步代码块的范围内调用,否则会报 Java.lang.IllegalMonitorStateException
            //  2. 必须调用 "同步" 对象的 wait和notify,notifyAll方法,  实例同步方法-this, 静态同步方法- Class.class , 同步块-  synchronized (obj) 中的obj
            obj.notify();
            if (num < 200) {
                System.out.println(Thread.currentThread().getName() + "::" + num++);
            } else {
                return false;
            }
            try {
                // wait 和 notify 必须在同代码库的范围内调用
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 实例同步方法
     *
     * @return
     */
    public synchronized boolean add1() {
        // synchronized (obj) {
        //  1. wait 和 notify 必须在两步代码块的范围内调用,否则会报 Java.lang.IllegalMonitorStateException
        //  2. 必须调用 "同步" 对象的 wait和notify,notifyAll方法,  实例同步方法-this, 静态同步方法- Class.class , 同步块-  synchronized (obj) 中的obj
        notify();
        if (num < 200) {
            System.out.println(Thread.currentThread().getName() + "::" + num++);
        } else {
            return false;
        }
        try {
            // wait 和 notify 必须在同代码库的范围内调用
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 静态同步方法
     *
     * @return
     */
    public static synchronized boolean add() {
        // synchronized (obj) {
        //  1. wait 和 notify 必须在两步代码块的范围内调用,否则会报 Java.lang.IllegalMonitorStateException
        //  2. 必须调用 "同步" 对象的 wait和notify,notifyAll方法,  实例同步方法-this, 静态同步方法- Class.class , 同步块-  synchronized (obj) 中的obj
        Work.class.notify();
        if (num < 200) {
            System.out.println(Thread.currentThread().getName() + "::" + num++);
        } else {
            return false;
        }
        try {
            // wait 和 notify 必须在同代码库的范围内调用
            Work.class.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}

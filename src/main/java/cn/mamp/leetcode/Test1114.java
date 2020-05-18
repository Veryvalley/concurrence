package cn.mamp.leetcode;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mamp
 * @data 2020/5/16
 */
public class Test1114 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        String str = scanner.next();
        str = str.replace("[", "").replace("]", "");
        String[] strs = str.split(",");
        Foo1 foo = new Foo1();
        for (String s : strs) {
            if ("1".equals(s)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        foo.one();
                    }
                }, "A").start();
            } else if ("2".equals(s)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        foo.two();
                    }
                }, "B").start();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        foo.three();
                    }
                }, "C").start();
            }
        }

    }
}

class Foo {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    private int flag = 1;


    public void one() {
        lock.lock();
        try {
            if (flag != 1) {
                conditionA.await();
            }
            System.out.print("one");
            flag = 2;
            conditionB.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void two() {

        lock.lock();
        try {
            if (flag != 2) {
                conditionB.await();
            }
            System.out.print("two");
            flag = 3;
            conditionC.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void three() {
        lock.lock();
        try {
            if (flag != 3) {
                conditionC.await();
            }
            System.out.print("three");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


class Foo1 {
    int flag = 1;
    public void one() {
        synchronized (this) {
            try {
                if (flag != 1) {
                    this.wait();
                }
                System.out.print("one");
                flag = 2;
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void two() {
        synchronized (this) {
            try {
                if (flag != 2) {
                    this.wait();
                }
                System.out.print("two");
                flag = 3;
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

    }

    public void three() {
        synchronized (this) {
            try {
                if (flag != 3) {
                    this.wait();
                }
                System.out.print("three");
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}

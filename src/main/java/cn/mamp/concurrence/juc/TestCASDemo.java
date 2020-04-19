package cn.mamp.concurrence.juc;

/**
 * CAS : compare-and-swap
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestCASDemo {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101 + 1));
                    System.out.println(Thread.currentThread().getName() + " ::result: " + b + " now:::" + cas.get());
                }
            }).start();
        }
    }
}

class CompareAndSwap {
    /**
     * 模拟主内存中的值
     */
    private int value;

    /**
     * 获取主内存值
     *
     * @return
     */
    public synchronized int get() {
        return value;
    }

    /**
     * 比较
     *
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        // 1. 获取内存的值, oldValue值就是获取到当前内存的值
        int oldValue = value;
        // 2. 如果预估的值与内存中的值(oldValue)相等,那么进行更新操作
        if (expectedValue == oldValue) {
            this.value = newValue;
        }
        // 否则,不进行任何操作,直接返回主内存中的值
        return oldValue;
    }

    /**
     * 设置
     *
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}


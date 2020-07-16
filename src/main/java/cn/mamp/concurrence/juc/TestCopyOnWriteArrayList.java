package cn.mamp.concurrence.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet "写入并复制"
 * 只能保证最终一致性
 * 注意: 添加操作多时效率低,因为得奖每次添加操作都是复制整个list,并发迭代操作多时可以使用
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        HelloThread ht = new HelloThread();
        // 并发修改异常 java.util.ConcurrentModificationException
        for (int i = 0; i < 5; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable {
    //  Collections.synchronizedList 的作用是 让 List里的方法都变成同步方法
    // 并发修改异常 java.util.ConcurrentModificationException
    // private static List<String> list = Collections.synchronizedList(new ArrayList<String>());


    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();

    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(Thread.currentThread().getName() + ":::" + it.next());
            list.add("aa");
        }
    }
}

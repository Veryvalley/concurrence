package cn.mamp.concurrence.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程的方法一共有四种 ,callable是第三种, 第四种是线程池
 * 1. 相较于Runnable ,Callable有返回值,并且可以抛出异常
 * 2. 执行Callable方式,需要FutureTask实现类的支持,用于接收返回结果, FutureTask 是Future 的实现类
 *
 * @author mamp
 * @data 2020/4/19
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        // 1. 执行Callable方式,需要FutureTask实现类的支持,用于接收返回结果
        FutureTask<Integer> result = new FutureTask<Integer>(td);
        new Thread(result).start();

        // 2. 接收执行的最后结果, 在计算线程执行完成前,会阻塞等待, 可用于闭锁
        try {
            int sum = result.get();
            System.out.println("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}
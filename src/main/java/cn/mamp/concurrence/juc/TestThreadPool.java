package cn.mamp.concurrence.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 1. 线程池: 提供了一个线程队列,队列中一定数据的等待状态的线程,需要时可以直接使用,避免了创建和销毁线程时的开销,提高响应速度
 *
 * 2. 线程池的体系结构
 * java.util.concurrent.Executor  线程使用和调试的根接口
 *      |-- ExecutorServcie***  Executor的子接口, 线程池的主要接口[重要]
 *          |-- ThreadPoolExecutor  线程池(ExecuotrServcie)的实现类
 *          |-- ScheduledExecutorService  ExecuotrServcie的子接口
 *              |-- ScheduledThreadPoolExecutor  继承ThreadPoolExecutor,实现ScheduledExecutorService 接口
 *
 * 3. 工具类: Executors
 *          ExecutorServcie newFixedThreadPool(); 创建固定大小的线程池
 *          ExecutorService newCachedThreadPool(); 缓存线程池, 线程池的大小不固定, 可以根据需要动态调整大小
 *          ExecutorService newSingleThreadPool(); 创建只有一个线程的线程池
 *
 *          ScheduledExecutorService newScheduledThreadPool(); 创建固定大小,可以延迟或者定时执行的线程池
 * @author mamp
 * @data 2020/5/7
 */
public class TestThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
       //runnableDemo();
       // callableDemo();
        scheduleThreadPoolDemo();
    }


    public static void runnableDemo(){
        // 1. 创建线程池

        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 2. 在线程池中提交任务
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        // 3. 关闭线程池
        pool.shutdown();
    }

    public static void callableDemo() throws ExecutionException, InterruptedException {
        // 1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5  );

        // 2. 提交任务到线程池

        List<Future<Integer>> futures = new ArrayList<>(); // 保存callable返回结果

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i1 = 0; i1 <= 100; i1++) {
                        sum += i1;
                    }
                    return sum;
                }
            });
            futures.add(future);
        }

        for (Future result: futures){
            System.out.println(result.get());
        }

        // 3. 关闭线程池
        pool.shutdown();
    }

    public static void scheduleThreadPoolDemo() throws ExecutionException, InterruptedException {
        // 1. 创建线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5 );

        // 2. 提交任务到线程池

        for (int i = 0; i < 3; i++) {
            Future<Integer> future = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName()+": "+sum);
                    return sum;
                }
            },3,TimeUnit.SECONDS);
            System.out.println(future.get());
        }

        // 3. 关闭线程池
        pool.shutdown();
    }
}

package cn.mamp.ds;

/**
 * 递归
 *
 * @author mamp
 * @data 2020/5/11
 */
public class Recursion {
}

/**
 * 斐波那契
 */
class Fibonacci {
    /**
     * 1 1 2 3 5 8 13
     * fn() = fn(n-1) + fn(n-2)
     * 时间复杂度: O(2^n) ***===***
     * *****************递归要慎用,性能问题, 容易栈溢出****************
     *
     * @param n
     * @return
     */
    public static int fab(int n) {
        // 递归终止条件
        if (n <= 2) {
            return 1;
        }
        return fab(n - 1) + fab(n - 2);
    }

    /**
     * 所有递归方法都可以用 循环来实现
     *
     * @param n
     * @return
     */
    public static int noFab(int n) {
        if (n <= 2) {
            return 1;
        }
        int a = 1;
        int b = 1;
        int c = 0;
        for (int i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public static int[] data;

    /**
     * 递归的优化 01: 缓存重复数据(中间的运算结果)
     *
     * @param n
     */

    public static int fab_01(int n) {
        if (n < 3) {
            return 1;
        }
        if (data[n - 1] > 0) {
            return data[n - 1];
        }
        data[n - 1] = fab_01(n - 1) + fab_01(n - 2);
        return data[n - 1];
    }

    /**
     * 尾递归: return 必须是一个单独的方法, 时间复杂度O(n)
     * 递归的方法的 return 有几个自调用,入参数一般就会多几个参数
     * 递归 先写出递归,再推导出尾递归
     *
     * @param n
     * @param prePreResult 上上次计算结果
     * @param preResult    上次计算结果
     * @return
     */
    public static long tailFab(long n, long prePreResult, long preResult) {
        if (n <= 2) {
            // 返回最近一次的结果
            return preResult;
        }
        // 本次计算结果 = 上上次结果 + 上次计算结果
        long curResult = prePreResult + preResult;

        prePreResult = preResult;
        preResult = curResult;
        return tailFab(n - 1, prePreResult, preResult);

    }

    public static void main(String[] args) {

        data = new int[100];
        // 通过缓存中间数据 , 优化后的递归, 很快算出结果
        System.out.println(fab_01(100));
        // 尾递归, 很快算出结果
        System.out.println(tailFab(100L, 1L, 1L));
        // 非常递归, 用普通的循环,很快算出结果
        System.out.println(noFab(100));
        //  普通递归算不出不, 会内存溢出的, ***** 慎重用普通递归*****
        System.out.println(fab(100));

    }
}
package cn.mamp.j2se.base;

import java.math.BigInteger;

/**
 * 位运算符: 左移, 无符号右移, 有符号右移
 * <p>
 * 机器数: 是带符号的, 最高位表示符号, 正数是0, 负数是 1
 * 真值:
 * <p>
 * 正数的原码,反码,补码 都是一样的
 * 负数的原码最高位是0,
 * 负数的反码是除最高位 外反
 * 负数的补码是反码加1
 * <p>
 * 计算机中只有加法运算, 没有减法, 减法转换成成 加一个"负数"
 * 实际上运算的是两个数的补码
 *
 * >>> 是无符号位移, 无论正数和负数都是最高位补0(操作是的补码, 所以负数操作一位后结果不是 /2)
 * @author mamp
 * @date 2020/7/22
 */
public class BitOperator {
    public static void main(String[] args) {

       // testMod();
        // 逻辑左移一位,相当于 *2
        int i = 4;
        System.out.println(i << 1);
        // 结果是: 4

        // 逻辑右移一份,相当于 /2
        int j = 4;
        // 结果是: 2
        System.out.println(j >> 1);


        int k = -8;
        System.out.println("==:" + decimalTiBinary(-8));
        // ==:-1000

        System.out.println(k >> 1);
        // 结果是: -4

        System.out.println("==:" + decimalTiBinary(k >> 1));
        // ==:-100

        System.out.println(k >>> 1);
        // 结果是: 2147483644

        System.out.println(decimalTiBinary(k >>> 1));
        // ==:01111111111111111111111111111100


        // ***Integer.toBinaryString(-8) 这个该当返回的是补码, 正数的话和原码一样, 负数和原码不一样
        System.out.println(Integer.toBinaryString(-8 >>> 1));
        System.out.println(Integer.toBinaryString(-8 >> 1));

        System.out.println(binaryToDecimal(Integer.toBinaryString(-8 >>> 1)));
        System.out.println(-8 >> 1);
        System.out.println(Integer.toBinaryString(-8 >> 1));
       // System.out.println(Integer.valueOf(Integer.toBinaryString(-8 >> 1),10));


    }

    /**
     * 十进制转二进制
     *
     * @param decimalSource 十进制数字
     * @return 二进制字符串
     */
    public static String decimalTiBinary(int decimalSource) {
        BigInteger bi = new BigInteger(String.valueOf(decimalSource));
        // 这里参数2,代码转为二进制
        return bi.toString(2);
    }

    /**
     * 二进制转为 10进制
     *
     * @param binarySource 二进制数据
     * @return 10进制数据
     */
    public static int binaryToDecimal(String binarySource) {
        //转换为BigInteger类型，参数2指定的是二进制
        BigInteger bi = new BigInteger(binarySource, 2);
        //默认转换成十进制
        return Integer.parseInt(bi.toString());
    }

    /**
     * 比较通过位运算判断奇偶数和取模判断的速度
     */

    public static void testMod() {


        int even_cnt = 0, odd_cnt = 0;
        long start = 0, end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {

            if ((i & 1) == 0) {
                even_cnt++;
            } else {
                odd_cnt++;
            }

        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(even_cnt + " " + odd_cnt);

        even_cnt = 0;
        odd_cnt = 0;
        start = 0;
        end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {

            if ((i % 2) == 0) {
                even_cnt++;
            } else {
                odd_cnt++;
            }

        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(even_cnt + " " + odd_cnt);

    }

}

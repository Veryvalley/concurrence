package cn.mamp.j2se.collection;

import java.io.IOException;

/**
 * 1byte = 8bit
 * 1int = 4byte = 32bit
 * 1foat = 4byte = 32bit
 * 1long  = 8byte = 64bit
 * 1duboule = 8byte = 64bit
 * <p>
 * << 左移: 低位补0 , 左移n位,相当于 乘以 2的n次方
 * >> 右移: 高位补0 , 右移n位,相当于 除以 2的n次方
 * >>> 无符号右移
 * <p>
 * 可以运用在快速查找、去重、排序、压缩数据等。
 *
 * @author mamp
 * @data 2020/5/10
 */
public class TestBitMap {
}

class BitMap {
    private byte[] bytes;
    private int max;

    public BitMap(int max) {
        this.max = max;
        this.bytes = new byte[(max >> 3) + 1];
    }

    /**
     * 向BitMap中添加数据
     *
     * @param n 需要添加的数据
     */
    public void add(int n) {
        if (n > max) {
            System.out.println(" [ERROR:]" + n + "超过最大数");
            return;
        }
        // n / 8 取整
        int bytesIndex = n >> 3;
        // n % 8 : n在 bytes[bytesIndex] 中的位置
        int loc = n & 0x07;
        // 存数据是时候用, 逻辑或,
        bytes[bytesIndex] |= (1 << loc);
    }

    /**
     * 从bitmap中删除 n
     *
     * @param n 要删除的数
     */
    public void del(int n) {
        int byteIndex = n >> 3;
        int loc = n & 0x07;
        // 删除与添加的区别: 先 取反, 再逻辑与
        bytes[byteIndex] &= ~(1 << loc);
    }

    /**
     * 在BitMap中查询 n是否存在
     *
     * @param n 查找的数
     * @return 结果, true-存在, false-不存在
     */
    public boolean find(int n) {
        // n / 8 取整
        int bytesIndex = n >> 3;
        // n % 8 : n在 bytes[bytesIndex] 中的位置
        int loc = n & 0x07;
        // 判断 bytes[bytesIndex]  的 loc 位是否为1
        // 查找数据是否存在时用 逻辑与
        int flag = bytes[bytesIndex] & (1 << loc);
        // 结果为0是不存在, 非0时存在
        return !(flag == 0);
    }

    /**
     * 计算bitMap中 1的个数
     *
     * @return
     */
    public int bitCount() {
        // TODO 参考Integer和Long的 bitCount 方法
        Integer.bitCount(100);
        return 0;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BitMap bitMap = new BitMap(2000000000);
        bitMap.add(2);
        bitMap.add(999);
        bitMap.add(888);

        System.out.println(bitMap.find(999));
        System.out.println(bitMap.find(666));
        System.out.println(bitMap.find(2));
        bitMap.del(999);
        System.out.println(bitMap.find(999));

       /* for (int i = 1000000000; i < 2000000000; i++) {
            bitMap.add(i);
        }
        System.out.println(bitMap.find(1900000000));
        long end = System.currentTimeMillis();
        System.out.println("用时:" + (end - start));

        BufferedWriter bw = new BufferedWriter(new FileWriter("d:\\testBitMap.txt"));
        for (int i = 1000000000; i < 2000000000; i++) {
            if (bitMap.find(i)) {
                bw.write(i + "");
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
        long end1 = System.currentTimeMillis();
        System.out.println("写入用时:" + (end1 - end));*/

    }
}
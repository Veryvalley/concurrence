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

class Test {

    /**
     * 创建bitmap数组
     */
    public byte[] create(int n) {
        byte[] bits = new byte[getIndex(n) + 1];

        for (int i = 0; i < n; i++) {
            add(bits, i);
        }

        System.out.println(contains(bits, 11));

        int index = 1;
        for (byte bit : bits) {
            System.out.println("-------" + index++ + "-------");
            showByte(bit);

        }

        return bits;
    }

    /**
     * 标记指定数字（num）在bitmap中的值，标记其已经出现过<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了
     *
     * @param bits
     * @param num
     */
    public void add(byte[] bits, int num) {
        bits[getIndex(num)] |= 1 << getPosition(num);
    }

    /**
     * 判断指定数字num是否存在<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
     *
     * @param bits
     * @param num
     * @return
     */
    public boolean contains(byte[] bits, int num) {
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }

    /**
     * num/8得到byte[]的index
     *
     * @param num
     * @return
     */
    public int getIndex(int num) {
        return num >> 3;
    }

    /**
     * num%8得到在byte[index]的位置
     *
     * @param num
     * @return
     */
    public int getPosition(int num) {
        return num & 0x07;
    }

    /**
     * 重置某一数字对应在bitmap中的值<br/>
     * 对1进行左移，然后取反，最后与byte[index]作与操作。
     *
     * @param bits
     * @param num
     */
    public void clear(byte[] bits, int num) {
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }

    /**
     * 打印byte类型的变量<br/>
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */

    public void showByte(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }

        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        int n = 100;
        new Test().create(n);
    }
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
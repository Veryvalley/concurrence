package cn.mamp.concurrence.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author mamp
 * @data 2020/5/10
 */
public class TestUnsafe {

    public static void main(String[] args) {
        Unsafe unsafe = TestUnsafe.reflectGetUnSafe();
        // long l = unsafe.allocateMemory(1024);
        // unsafe.reallocateMemory(l, 1024);

        String[] strings = new String[]{"1", "2", "3", "4", "5"};
        String[] strings1 = new String[]{"8", "2", "3", "4", "5"};
        Object obj = new Object();
       // long i = unsafe.arrayBaseOffset(String[].class);
        long i = unsafe.arrayBaseOffset(Object.class);
        System.out.println("string[] base offset is :" + i);

        //every index scale
        long scale = unsafe.arrayIndexScale(String[].class);
        scale = unsafe.arrayIndexScale(Object.class);
        System.out.println("string[] index scale is " + scale);

        //print first string in strings[]
        System.out.println("first element is :" + unsafe.getObject(strings1, i));

        //set 100 to first string
        unsafe.putObject(strings, i + scale * 0, "100");

        //print first string in strings[] again
        System.out.println("after set ,first element is :" + unsafe.getObject(strings, 20 + scale * 0));

        System.out.println(0xa);

    }

    /**
     * 通过反射方式获得 Unsafe 对象
     *
     * @return
     */
    public static Unsafe reflectGetUnSafe() {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unsafe;
    }
}

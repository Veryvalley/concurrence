package cn.mamp;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
        System.out.println(1 << 16);
        System.out.println(2 & 1);
        System.out.println(16 >> 5);

        System.out.println(~4); // 0100 -> 1011
        System.out.println(5 ^ 9); // 0101 / 1001

        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>();

    }

    @Test
    public void test1() {
        System.out.println(1 << 16);
    }

}

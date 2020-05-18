package cn.mamp;

import org.junit.Test;

import java.util.UUID;
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

        System.out.println(1L >>> 63);
        System.out.println(Integer.bitCount(7)); // 00000111
        System.out.println(Long.bitCount(1L));

        System.out.println((1 | 1 | 0) == 1);
    }

    @Test
    public void testUUID() {
        // UUID uuid = new UUID();
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void testAssert() {
        try {
            boolean flag = 1 == 2;
            assert flag;
            System.out.println("xxx");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

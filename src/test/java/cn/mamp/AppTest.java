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

        System.out.println(1L >>> 63);
        System.out.println(Integer.bitCount(7)); // 00000111
        System.out.println(Long.bitCount(1L));

        System.out.println((1 | 1 | 0) == 1);
    }

    @Test
    public void testUUID() {
        // UUID uuid = new UUID();
        //System.out.println(UUID.randomUUID());

        int total = 129;
        int pageSize = 10;
//        System.out.println(a/ 10);

        int totalPage =  (int)(Math.ceil(total*1.0/pageSize));
        System.out.println(totalPage);
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

    @Test
    public void testTest() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT TASKDATE.DATA_DATE, TASKDATE.EXEC_STATUS AS TASK_DATE_STATUS, ")
                .append(" CC.CEP_EVENT_ID, CC.STREAM_EVENT_ID, CC.EVENT_PARAM_JSON, MCD.PLAN_ID, TASK.* ,EXT.PUSH_TYPE FROM ")
                .append(" ( ")
                .append(" SELECT A.DATA_DATE, A.TASK_ID, B.EXEC_STATUS FROM ( ")
                .append(" SELECT MAX(T.DATA_DATE) DATA_DATE, T.TASK_ID FROM MCD_CAMP_TASK_DATE T GROUP BY T.TASK_ID ")
                .append(" ) A, ")
                .append(" MCD_CAMP_TASK_DATE B WHERE A.DATA_DATE = B.DATA_DATE AND A.TASK_ID = B.TASK_ID ")
                .append(" ) ")
                .append(" TASKDATE, MCD_CAMP_TASK TASK, MCD_CAMP_CHANNEL_LIST CC, MCD_CAMP_DEF MCD ,MCD_CAMP_CHANNEL_EXT_JX EXT ")
                .append(" WHERE TASKDATE.TASK_ID = TASK.TASK_ID AND CC.CAMPSEG_ID = TASK.CAMPSEG_ID AND CC.CHANNEL_ID = TASK.CHANNEL_ID AND ")
                .append("  CC.CAMPSEG_ID = EXT.CAMPSEG_ID AND CC.CHANNEL_ID = EXT.CHANNEL_ID  AND ")
                .append(" CC.CAMPSEG_ID = MCD.CAMPSEG_ID AND CC.CHANNEL_ID IN (901,801)")
                .append(" AND TASKDATE.EXEC_STATUS = ? AND TASK.EXEC_STATUS IN ('50', '51', '70', '71', '79') AND ")
                .append(" (SYSDATE BETWEEN TASK.TASK_START_TIME AND TASK.TASK_END_TIME) AND CC.CEP_EVENT_ID IS NOT NULL");
        System.out.println(sql);
    }

}

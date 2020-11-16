package cn.mamp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author Administrator
 * @data 2020/11/13
 */
public class RedisDemo {
    private static final Logger logger = LoggerFactory.getLogger(RedisDemo.class);

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        //连接
        jedis.connect();
        //jedis.set("a", "123");
        //列出所有的key
        Set<String> keys = jedis.keys("*");

        for (String key : keys) {
            logger.info("key:{}", key);
        }

        testS();
    }


    public  static void testS() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(8);
        config.setMaxWaitMillis(1000);
        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, 10000, null, 0);
        Jedis jedis = jedisPool.getResource();
        User1 user = new User1();
        user.setId("1");
        user.setGender("男");
        user.setNickname("cherish");
        user.setPassword("123456");
        user.setUsername("admin");

        FastjsonRedisSerializer fastjsonRedisSerializer = new FastjsonRedisSerializer();

        jedis.set("user".getBytes(), SerializeUtil.serialize(user));
        //jedis.set("user".getBytes(), fastjsonRedisSerializer.serialize(user));
        System.out.println("--------------------------------------------------------------------");
        try {

            User1 userResult = (User1) SerializeUtil.deserialize(jedis.get("user".getBytes()));
           //  User1 userResult = (User1) fastjsonRedisSerializer.deserialize(jedis.get("user".getBytes()));
            System.out.println(userResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


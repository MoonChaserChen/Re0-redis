package ink.akira.re0.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {
    @Test
    public void testLuaScript(){
        Jedis jedis = new Jedis("localhost", 6379);
        long count = (long) jedis.eval("return #redis.call('keys', ARGV[1])", 0, "blog-read-count:*");
        System.out.println(count);
    }
}

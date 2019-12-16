package ink.akira.re0.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisTest {
    @Test
    public void testLuaScript(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            long count = (long) jedis.eval("return #redis.call('keys', ARGV[1])", 0, "blog-read-count:*");
            System.out.println(count);
        }
    }

    @Test
    public void testMulti(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            Pipeline pipelined = jedis.pipelined();
            pipelined.multi();
            String teacherId = "1143013021";
            String courseId = "en-118";
            pipelined.sadd("teacher-id", teacherId);
            pipelined.sadd("course-id", courseId);
            pipelined.sadd("teacher-course", teacherId, courseId);
            pipelined.exec();
            pipelined.sync();
        }
    }
}

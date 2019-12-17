package ink.akira.re0.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

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

    @Test
    public void testMultiIncrFalse(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            Pipeline pipelined = jedis.pipelined();
            // 监控mykey以防止被其它client修改
            pipelined.watch("mykey");
            Response<String> myKeyResp = pipelined.get("mykey");
            int myKey = Integer.parseInt(myKeyResp.get());
            // 开启事务
            pipelined.multi();
            // 执行增加1操作
            pipelined.set("mykey", String.valueOf(myKey + 1));
            pipelined.exec();
        }
    }

    @Test
    public void testMultiIncr(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.watch("mykey");
            int myKey = Integer.parseInt(jedis.get("mykey"));
            Transaction transaction = jedis.multi();
            transaction.set("mykey", String.valueOf(myKey + 1));
            transaction.exec();
        }
    }
}

package ink.akira.re0.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JedisSentinelTest {
    @Test
    public void test() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        Set<String> sentinels = new HashSet<>(Arrays.asList(
                "localhost:26379",
                "localhost:26379",
                "localhost:26379"
        ));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(5);

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig);
        try (Jedis jedis = jedisSentinelPool.getResource()) {

        }
    }
}

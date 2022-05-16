package net.lastcraft.base.redis;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.lastcraft.base.sql.ConnectionConstants;
import redis.clients.jedis.Jedis;

@UtilityClass
public class Redis {

    public Jedis newJedisInstance() {
        return new Jedis("s1" + ConnectionConstants.DOMAIN.getValue(), 6379);
    }

    public Jedis authenticateJedis(@NonNull Jedis jedis) {
        jedis.auth(ConnectionConstants.PASSWORD.getValue());
        return jedis;
    }
}

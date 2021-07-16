package pw.mihou.database;

import pw.mihou.Velia;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisDB {

    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), System.getenv("redis_host"),
            Integer.parseInt(System.getenv("redis_port")), Protocol.DEFAULT_TIMEOUT, System.getenv("redis_pass"));

    /**
     * Ping Redis to see if it is alive or
     * not, it should reply with a response like PONG if it
     * is alive.
     */
    public static void ping() {
        Velia.log.debug("Attempting to ping Redis server.");
        try (Jedis jedis = pool.getResource()) {
            Velia.log.debug("Received response from ping to Redis host: {}", jedis.ping());
        }
    }

    /**
     * Gets the value of a key inside the database.
     *
     * @param key The value of a key inside the database.
     * @return The value returned.
     */
    public static String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            String response = jedis.get(key);
            Velia.log.debug("Response from Redis for ({}): {}", key, response);
            return response;
        }
    }

    /**
     * Does this key exist in the database?
     *
     * @param key The key to search for.
     * @return DOes the key exist?
     */
    public static boolean exists(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.exists(key);
        }
    }

    /**
     * Adds or sets the value of the key.
     *
     * @param key The key to change.
     * @param value The value to add/change.
     */
    public static void set(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            Velia.log.debug("Response from redis for (set: [key: {}, value: {}]): {}", key, value, jedis.set(key, value));
        }
    }

}

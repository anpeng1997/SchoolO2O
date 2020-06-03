package cn.pengan.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
    private JedisPool jedisPool;

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds 以秒为单位
     */
    public void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        jedis.close();
    }

    /**
     * redis string 类型操作
     */
    public class Strings {

        public Strings(JedisUtil jedisUtil) {
        }

        /**
         * 根据key值来获取value
         *
         * @param key
         * @return
         */
        public String get(String key) {
            Jedis jedis = getJedis();
            String value = jedis.get(key);
            jedis.close();
            return value;
        }

        public byte[] get(byte[] key) {
            Jedis jedis = getJedis();
            byte[] bytes = jedis.get(key);
            jedis.close();
            return bytes;
        }

        /**
         * 添加一条记录，如果存在记录就覆盖
         *
         * @param key
         * @param value
         * @return
         */
        public String set(String key, String value) {
            Jedis jedis = getJedis();
            String str = jedis.set(key, value);
            jedis.close();
            return str;
        }

        public String set(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            String str = jedis.set(key, value);
            jedis.close();
            return str;
        }

        /**
         * 添加一条记录，仅当给定的key不存在时才插入
         *
         * @param key
         * @param value
         * @return long 状态码，1插入成功且key不存在，0未插入，key存在
         */
        public long setnx(String key, String value) {
            Jedis jedis = getJedis();
            Long setnx = jedis.setnx(key, value);
            jedis.close();
            return setnx;
        }

        /**
         * 添加有过期时间的value
         *
         * @param key
         * @param value
         * @param seconds 以秒为单位
         * @return
         */
        public String setEx(String key, String value, int seconds) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            jedis.close();
            return str;
        }

        public String setEx(byte[] key, byte[] value, int seconds) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            jedis.close();
            return str;
        }

        /**
         * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
         * 例:String str1="123456789";<br/>
         * 对str1操作后setRange(key,4,0000)，str1="123400009";
         *
         * @param key
         * @param offset
         * @param value
         * @return long value的长度
         */
        public long setRange(String key, long offset, String value) {
            Jedis jedis = getJedis();
            long len = jedis.setrange(key, offset, value);
            jedis.close();
            return len;
        }

        /**
         * 在指定的key中追加value
         *
         * @param key
         * @param value
         * @return long 追加后value的长度
         **/
        public long append(String key, String value) {
            Jedis jedis = getJedis();
            long len = jedis.append(key, value);
            jedis.close();
            return len;
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}

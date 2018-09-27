package com.dcpro.demo.controller.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	  //Redis鏈嶅姟鍣↖P
    private static String ADDR = "127.0.0.1";
    
    //Redis鐨勭鍙ｅ彿
    private static int PORT = 6379;
    
    
    //鍙敤杩炴帴瀹炰緥鐨勬渶澶ф暟鐩紝榛樿鍊间负8锛�
    //濡傛灉璧嬪�间负-1锛屽垯琛ㄧず涓嶉檺鍒讹紱濡傛灉pool宸茬粡鍒嗛厤浜唌axActive涓猨edis瀹炰緥锛屽垯姝ゆ椂pool鐨勭姸鎬佷负exhausted(鑰楀敖)銆�
    private static int MAX_ACTIVE = 1024;
    
    //鎺у埗涓�涓猵ool鏈�澶氭湁澶氬皯涓姸鎬佷负idle(绌洪棽鐨�)鐨刯edis瀹炰緥锛岄粯璁ゅ�间篃鏄�8銆�
    private static int MAX_IDLE = 200;
    
    //绛夊緟鍙敤杩炴帴鐨勬渶澶ф椂闂达紝鍗曚綅姣锛岄粯璁ゅ�间负-1锛岃〃绀烘案涓嶈秴鏃躲�傚鏋滆秴杩囩瓑寰呮椂闂达紝鍒欑洿鎺ユ姏鍑篔edisConnectionException锛�
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //鍦╞orrow涓�涓猨edis瀹炰緥鏃讹紝鏄惁鎻愬墠杩涜validate鎿嶄綔锛涘鏋滀负true锛屽垯寰楀埌鐨刯edis瀹炰緥鍧囨槸鍙敤鐨勶紱
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 鍒濆鍖朢edis杩炴帴姹�
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 鑾峰彇Jedis瀹炰緥
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 閲婃斁jedis璧勬簮
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
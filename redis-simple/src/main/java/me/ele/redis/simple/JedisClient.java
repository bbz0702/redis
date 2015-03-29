package me.ele.redis.simple;

import me.ele.redis.simple.JedisCallback.DoInJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis客服端服务类
 * 
 * @author wangzhiping
 *
 */
public class JedisClient {

	/**
	 * 默认配置文件
	 */
	public static final String DEFAULT_REDIS_PROPERTIES = "/redis.properties";

	/**
	 * Jedis 配置类
	 */
	private JedisPoolConfig config;

	/**
	 * Jedis 连接池
	 */
	private JedisPool jedisPool;

	private JedisProperties jedisProperties;

	private JedisClient() {
		init();
	}

	/**
	 * 初始化Jedis连接池
	 */
	private void init() {

		if (config == null) {
			config = new JedisPoolConfig();
			jedisProperties = new JedisProperties(DEFAULT_REDIS_PROPERTIES);
			config.setMaxTotal(jedisProperties.getPoolCapacity());
			config.setMaxIdle(jedisProperties.getMaxIdle());
			config.setMinIdle(jedisProperties.getMinIdle());
			config.setTestOnBorrow(jedisProperties.isTestBorrow());
			config.setTestOnReturn(jedisProperties.isTestReturn());
		}

		jedisPool = new JedisPool(config, jedisProperties.getHost(),
				jedisProperties.getPort(), jedisProperties.getTimeout(),
				jedisProperties.getPassword(),
				jedisProperties.getDatabaseNumber(),
				jedisProperties.getCientName());

	}

	// 构建单例
	private static class JedisClientCreator {
		private static JedisClient client = new JedisClient();
	}

	public static JedisClient getInstance() {
		return JedisClientCreator.client;
	}

	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 释放Jedis
	 * 
	 * @param jedis
	 *            Jedis实例
	 * @param isBroken
	 *            true：释放坏的Jedis，false：释放好的Jedis
	 */
	public void releaseJedis(Jedis jedis, Boolean isBroken) {

		if (jedis != null) {
			if (!isBroken || isBroken == null) {
				jedisPool.returnResource(jedis);
			} else {
				jedisPool.returnBrokenResource(jedis);
			}
			jedis = null;
		}
	}

	/**
	 * Jedis方法执行模板
	 * @param doInJedis
	 * @return
	 */
	public <T> T execute(JedisCallback.DoInJedis<T> doInJedis) {

		Jedis jedis = null;

		try {
			jedis = this.getJedis();
			return doInJedis.doIn(jedis);
		} catch (Exception e) {
			this.releaseJedis(jedis, true);
			e.printStackTrace();
		} finally {
			this.releaseJedis(jedis, false);
		}

		return null;
	}
	
	
	public static void main(String[] args) {
	
		Object value = JedisClient.getInstance().execute(new DoInJedis<Object>() {
			
			public Object doIn(Jedis jedis) {
				jedis.set("username", "admin");
				return jedis.get("username");
			}
		});
		
		System.out.println(value);
		
	}

}

package me.ele.redis.simple;

import redis.clients.jedis.Jedis;
/**
 * 回调模板
 * @author wangzhiping
 *
 */
public class JedisCallback {
	
	
	/**
	 * 定义Jedis实例回调接口
	 * @author wangzhiping
	 *
	 * @param <T>
	 */
	public interface DoInJedis<T>{
		
		public T doIn(Jedis jedis);
		
	}

}

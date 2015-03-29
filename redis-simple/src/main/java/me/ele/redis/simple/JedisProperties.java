package me.ele.redis.simple;

import java.io.IOException;
import java.util.Properties;

/**
 * Jedis属性配置类
 * 
 * @author wangzhiping
 *
 */
public  class JedisProperties {
	
	private Properties properties;
	
	public JedisProperties(Properties properties) {
		this.properties = properties;
	}
	
	public JedisProperties(String redisPropertyFile){
		try {
			properties = new Properties();
			properties.load(JedisProperties.class.getResourceAsStream(redisPropertyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getHost(){
		return properties.getProperty(JedisProperty.REDIS_HOST.getProperyName());
	}
	
	public int getTimeout(){
		String timeoutString = properties.getProperty(JedisProperty.REDIS_TIMEOUT.getProperyName(), "1000");
		return Integer.parseInt(timeoutString);
	}
	
	public int getPort(){
		String portString = properties.getProperty(JedisProperty.REDIS_PORT.getProperyName(), "6379");
		return Integer.parseInt(portString);
	}
	
	public String getPassword() {
		return properties.getProperty(JedisProperty.REDIS_PASSWORD.getProperyName(), null);
	}
	
	public int getDatabaseNumber(){
		String databaseNumberString = properties.getProperty(JedisProperty.REDIS_DATABASE_NUMBER.getProperyName(), "0");
		return Integer.parseInt(databaseNumberString);
	}
	
	public String getCientName(){
		return properties.getProperty(JedisProperty.REDIS_CLIENT_NAME.getProperyName(), null);
	}
	
	public int getPoolCapacity(){
		String poolCapacityString = properties.getProperty(JedisProperty.REDIS_POOL_CAPACITY.getProperyName(), "100");
		return Integer.parseInt(poolCapacityString);
	}
	
	public int getMaxIdle(){
		String maxIdleString = properties.getProperty(JedisProperty.REDIS_POOL_MAXIDLE.getProperyName(), "0");
		return Integer.parseInt(maxIdleString);
	}
	
	public int getMinIdle(){
		String minIdleString =  properties.getProperty(JedisProperty.REDIS_POOL_MINIDLE.getProperyName(), "0");
		return Integer.parseInt(minIdleString);
	}
	
	public boolean isTestBorrow(){
		String testBorrowString =  properties.getProperty(JedisProperty.REDIS_POOL_TEST_BORROW.getProperyName(), "false");
		return Boolean.parseBoolean(testBorrowString);
	}
	
	public boolean isTestReturn(){
		String testReturnString =  properties.getProperty(JedisProperty.REDIS_POOL_TEST_RETURN.getProperyName(), "false");
		return Boolean.parseBoolean(testReturnString);
	}
	

	public enum JedisProperty {

		// redis连接属性
		
		REDIS_PASSWORD("redis.password"),

		REDIS_HOST("redis.host"),

		REDIS_PORT("redis.port"),
		
		REDIS_TIMEOUT("redis.timeout"),

		REDIS_DATABASE_NUMBER("redis.database.number"),

		REDIS_CLIENT_NAME("redis.client.name"),

		// redis 连接池属性
		REDIS_POOL_CAPACITY("redis.pool.capacity"),
		
		REDIS_POOL_MAXIDLE("redis.pool.maxidle"),
		
		REDIS_POOL_MINIDLE("redis.pool.minidle"),
		
		REDIS_POOL_TEST_BORROW("redis.pool.test.borrow"),
		
		REDIS_POOL_TEST_RETURN("redis.pool.test.return"),
		
		;

		private String propertyName;

		private JedisProperty(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getProperyName() {
			return this.propertyName;
		}

	}

}

# redis
Jedis实现各种redis场景

## redis-simple
该场景不依赖仅仅依赖Jedis的实现，并对使用进行了封装

* JedisClient

* JedisProperties

* JedisCallback

* JedisCallback.DoInJedis

### 使用方法

``` 
	1、在classpath下添加redis.properties文件，待添加的属性在类JedisProperties.JedisProperty中
	
	2、使用JedisClient.execute(JedisCallback<T> doInJedis)执行

```

### 范例

```
	public static void main(String[] args) {
	
		Object value = JedisClient.getInstance().execute(new DoInJedis<Object>() {
			
			public Object doIn(Jedis jedis) {
				jedis.set("username", "admin");
				return jedis.get("username");
			}
		});
		
		System.out.println(value);
		
	}

```
### 注意点

* 该模式友好支持仅有一台redis服务器提供读写服务

* 目前仅仅使用回调模板模式一次封装，后续会加入二次封装，以提供更好的使用方式

* 其中在JedisProperties定义的配置属性未完全覆盖，后续也将加入

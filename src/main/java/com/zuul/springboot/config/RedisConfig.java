package com.zuul.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <pre>
 * 功       能: 通过configuration value bean注解 将自定义bean托管给spring
 * 涉及版本: V3.0.0 
 * 创  建  者: yangyiwei
 * 日       期: 2018年3月29日 上午11:14:02
 * Q    Q: 2873824885
 * </pre>
 */
@Configuration
@PropertySource("classpath:redis.property")
public class RedisConfig {

	@Bean(name = "jedisPool")
	public JedisPool jedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig config,
			@Value("${jedis.pool.host}") String host, @Value("${jedis.pool.port}") int port,
			@Value("${jedis.pool.timeout}") int timeout, @Value("${jedis.pool.password}") String password) {
		return new JedisPool(config, host, port, timeout, password);
	}

	@Bean(name = "jedis")
	public Jedis jedis(@Qualifier("jedisPool") JedisPool jedisPool) {
		return jedisPool.getResource();// 返回一条长用连接
	}

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig(@Value("${jedis.pool.config.maxTotal}") int maxTotal,
			@Value("${jedis.pool.config.maxIdle}") int maxIdle,
			@Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}

}

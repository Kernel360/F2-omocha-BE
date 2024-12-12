package org.omocha.infra.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password}")
	private String password;

	@Bean
	public RedissonClient redissonClient() {
		String url = "redis://" + host + ":" + port;

		Config config = new Config();
		config.useSingleServer()
			.setAddress(url)
			.setPassword(password);

		return Redisson.create(config);
	}
}

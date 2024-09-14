package org.auction.client.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.auction.domain")
@EnableJpaRepositories(basePackages = "org.auction.domain")
public class JpaConfig {
}

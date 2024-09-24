package org.auction.client.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.auction.domain")
@EnableJpaRepositories(basePackages = "org.auction.domain")
@EnableJpaAuditing
public class JpaConfig {
}

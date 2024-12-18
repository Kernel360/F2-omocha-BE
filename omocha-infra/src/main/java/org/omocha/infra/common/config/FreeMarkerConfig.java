package org.omocha.infra.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreeMarkerConfig {

	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
		FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
		factoryBean.setTemplateLoaderPath("classpath:/templates/");
		factoryBean.setDefaultEncoding("UTF-8");
		return factoryBean;
	}
}

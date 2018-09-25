package com.zuul.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class }) // spring-boot
@ServletComponentScan // servlete组件扫描
public class ChatZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatZuulServiceApplication.class, args);
	}
}

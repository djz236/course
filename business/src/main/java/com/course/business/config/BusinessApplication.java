package com.course.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.course")
@MapperScan("com.course.server.mapper")
public class BusinessApplication {
private static final Logger LOG=LoggerFactory.getLogger(BusinessApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(BusinessApplication.class, args);
		LOG.info("启动成功");
		LOG.info("Business 地址：\thttp://127.0.0.1:{}",run.getEnvironment().getProperty("server.port"));

	}

}

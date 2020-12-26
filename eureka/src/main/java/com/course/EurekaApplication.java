package com.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
private static final Logger LOG=LoggerFactory.getLogger(EurekaApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(EurekaApplication.class, args);
		LOG.info("启动成功");
		LOG.info("Eureka 地址：\thttp://127.0.0.1:{}",run.getEnvironment().getProperty("server.port"));

	}

}

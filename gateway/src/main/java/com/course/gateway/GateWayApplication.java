package com.course.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class GateWayApplication {
private static final Logger LOG=LoggerFactory.getLogger(GateWayApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(GateWayApplication.class, args);
		LOG.info("启动成功");
		LOG.info("gateway 地址：\thttp://127.0.0.1:{}",run.getEnvironment().getProperty("server.port"));

	}
}

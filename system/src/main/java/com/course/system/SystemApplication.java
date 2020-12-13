package com.course.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SystemApplication {
private static final Logger LOG=LoggerFactory.getLogger(SystemApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(SystemApplication.class, args);
		LOG.info("启动成功");
		LOG.info("system 地址：\thttp://127.0.0.1:{}",run.getEnvironment().getProperty("server.port"));

	}

}

package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@EnableAspectJAutoProxy
public class ElectionManagementSystemApplication {

	public static void main(String[] args) {
			SpringApplication.run(ElectionManagementSystemApplication.class, args);
	}
}
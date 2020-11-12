package com.tuling.tulingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TulingmallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(TulingmallMemberApplication.class, args);
	}

}

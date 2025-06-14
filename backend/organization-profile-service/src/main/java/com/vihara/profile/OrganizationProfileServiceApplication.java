package com.vihara.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrganizationProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationProfileServiceApplication.class, args);
	}

}

package com.espe.moviles.bckmoviles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BckmovilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BckmovilesApplication.class, args);
	}

}

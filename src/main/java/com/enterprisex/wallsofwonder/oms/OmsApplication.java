package com.enterprisex.wallsofwonder.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients   // Only if you want to use Feign
public class OmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmsApplication.class, args);
	}


}

package io.github.vdubois;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudSampleAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudSampleAuthenticationServiceApplication.class, args);
	}
}

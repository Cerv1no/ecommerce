package com.cerv1no.ecommerce;

import jakarta.persistence.Entity;
import org.mapstruct.BeanMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.cerv1no.ecommerce.model")
@EnableJpaRepositories(basePackages = "com.cerv1no.ecommerce.repositories")
@SpringBootApplication(scanBasePackages = {"com.cerv1no.ecommerce", "com.cerv1no.ecommerce.mapper"})
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}

package com.acc.cloud.ms.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acc.cloud.ms.domain.Friend;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * The SwaggerConfig for managing {@link Friend} request friend interface.
 *
 * @author Laxminarayan Rajput
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.acc.cloud.ms.resource"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
			"Friend API",
			"This Friend API is being used to create, delete and find Friend details",
			"Friend API",
			"This is Accenture Workshop API",
			new Contact("Accenture", "www.accenture.com", "customersupport@accentrue.com"),
			"License of API", "Accenture", Collections.emptyList());
			
	}

}

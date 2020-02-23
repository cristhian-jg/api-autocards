package com.crisgon.apirest.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Cristhian González",
			"https://github.com/cristhian-jg/AutocartasAPI", "cristhianbay@gmail.com");

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Autocartas Api Documentation",
			"Autocartas API Documentation", "1.0", "PREMIUM", DEFAULT_CONTACT, "Apache 2.0",
			"https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO);
	}
}

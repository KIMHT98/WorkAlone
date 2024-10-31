package com.ssawallafy.workalone_backend.global.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		// String jwt = "JWT";
		// SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
		// Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
		// 	.name(jwt)
		// 	.type(SecurityScheme.Type.HTTP)
		// 	.scheme("bearer")
		// 	.bearerFormat("JWT")
		// );
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
			// .addSecurityItem(securityRequirement)
			// .components(components);
	}
	private Info apiInfo() {
		return new Info()
			.title("WorkAlone API") // API의 제목
			.description("WorkAlone의 Swagger") // API에 대한 설명
			.version("1.0.0"); // API의 버전
	}
}

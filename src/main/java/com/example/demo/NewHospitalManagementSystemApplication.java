package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hospital API", version = "2.0",
		description = "Hospital Management System | Developed by Ashish"))
@SecurityScheme(name = "securityinuseapi", scheme = "basic",
		type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class NewHospitalManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(NewHospitalManagementSystemApplication.class);
		app.setBannerMode(Banner.Mode.LOG);
		app.run(args);
	}

}

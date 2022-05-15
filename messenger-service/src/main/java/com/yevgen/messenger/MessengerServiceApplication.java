package com.yevgen.messenger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Messenger API", version = "0.0.1", description = "Simple Messenger API"))
public class MessengerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerServiceApplication.class, args);
	}

}

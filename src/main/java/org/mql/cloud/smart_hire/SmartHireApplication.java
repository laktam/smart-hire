package org.mql.cloud.smart_hire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class SmartHireApplication {

	public static void main(String[] args) {
//		if (System.getenv("AZURE_ENV") == null) { 
//            Dotenv dotenv = Dotenv.load();
//            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
//        }
		

		SpringApplication.run(SmartHireApplication.class, args);
	}

}

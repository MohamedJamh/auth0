package com.auth0;

import com.auth0._db.seeder.AppSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Auth0Application {


	public static void main(String[] args) {
		SpringApplication.run(Auth0Application.class, args);
	}

}

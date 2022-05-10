package br.com.unb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.unb.exception.ResponseExceptionHandler;

@SpringBootApplication
@Import(ResponseExceptionHandler.class)
public class PGOMApplication {

	public static void main(String[] args) {
		SpringApplication.run(PGOMApplication.class, args);
	}

}

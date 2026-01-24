package app.forgeon.forgeon_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "app.forgeon.forgeon_api.repository")
@EntityScan(basePackages = "app.forgeon.forgeon_api.model")
@ComponentScan(basePackages = "app.forgeon.forgeon_api")
public class ForgeonApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ForgeonApiApplication.class, args);
	}

}

package com.finspire.Grandpittu;

import com.finspire.Grandpittu.entity.Role;
import com.finspire.Grandpittu.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class GrandpittuApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrandpittuApplication.class, args);
		System.out.println(LocalDateTime.now());
	}

	@Bean
	public CommandLineRunner runner(RoleRepository repository){
		return args -> {
			if (repository.findByName("USER").isEmpty()){
				repository.save(
						Role.builder()
								.name("USER")
								.build()
				);
			}

		};
	}
}

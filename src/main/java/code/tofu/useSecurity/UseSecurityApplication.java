package code.tofu.useSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class UseSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UseSecurityApplication.class, args);
	}

}

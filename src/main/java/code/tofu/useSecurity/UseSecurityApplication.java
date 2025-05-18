package code.tofu.useSecurity;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Slf4j
@SpringBootApplication
public class UseSecurityApplication {

	@Autowired
	SecurityFilterChain securityFilterChain;

	@PostConstruct
	void printFilterChains(){
		List<Filter> filters = securityFilterChain.getFilters();
		filters.forEach(filter -> log.info("{}", filter.getClass().getSimpleName()));
	}


	public static void main(String[] args) {
		SpringApplication.run(UseSecurityApplication.class, args);
	}

}

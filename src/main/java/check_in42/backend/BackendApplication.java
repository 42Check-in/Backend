package check_in42.backend;

import check_in42.backend.presentation.utils.PresentationStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

		System.out.println(PresentationStatus.getOrdinalByDescription("대기 중"));

	}

}

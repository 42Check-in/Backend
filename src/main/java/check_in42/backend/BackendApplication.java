package check_in42.backend;

import check_in42.backend.presentation.utils.PresentationStatus;
import check_in42.backend.presentation.utils.PresentationType;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.visitUtils.PriorApproval;
import check_in42.backend.visitors.visitUtils.VisitPlace;
import check_in42.backend.visitors.visitUtils.VisitPurpose;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

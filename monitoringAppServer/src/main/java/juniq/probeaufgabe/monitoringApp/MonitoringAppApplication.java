package juniq.probeaufgabe.monitoringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringAppApplication.class, args);
	}

}

package juniq.probeaufgabe.monitoringApp.task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import juniq.probeaufgabe.monitoringApp.Repo.UrlRepo;
import juniq.probeaufgabe.monitoringApp.model.Url;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ScheduledTasks {
	
	private List<Url> urls =  new ArrayList<>();
	
	@Autowired
	private UrlRepo urlRepo;

	
	@PostConstruct
	public void populatingData() {
		Random random = new Random();
		//Mit Dummy Daten erzeugt und in Datenbank gefuellt
        Stream.of("Google", "Facebook", "juniq-It", "berlin", "hs-OSNABRUECK", "Angular").forEach(name -> {
            Url url = new Url(name,random.nextInt(255)+"."+random.nextInt(255)+"."+random.nextInt(255)+"."+random.nextInt(255),"https://www." + name.toLowerCase() + ".de", "UNKNOWN YET");
            urlRepo.save(url);
        });
		
		urls = (List<Url>) urlRepo.findAll();
	}
	
	//Die Methode sortiert die Rueckmeldung von Httpanfrage in Kategorien
	public String checkHttpStatusCode(int responseCode, String url) {
		String responseCodeStr = "";
		
		switch (responseCode) {
		case HttpURLConnection.HTTP_OK: log.info(url+" ---> UP");
			responseCodeStr = "UP"; break;
		case HttpURLConnection.HTTP_BAD_GATEWAY: log.warn(url+" ---> BAD GATEWAY");
			responseCodeStr =  "BAD GATEWAY"; break;
		case HttpURLConnection.HTTP_BAD_REQUEST: log.warn(url+" ---> BAD REQUEST");
			responseCodeStr =  "BAD_REQUEST"; break;
		case HttpURLConnection.HTTP_FORBIDDEN: log.warn(url+" ---> FORBIDDEN");
			responseCodeStr =  "FORBIDDEN"; break;
		case HttpURLConnection.HTTP_NOT_FOUND: log.warn(url+" ---> NOT FOUND/DOWN");
			responseCodeStr = "NOT FOUND/DOWN" ; break;
		case HttpURLConnection.HTTP_INTERNAL_ERROR: log.warn(url+" ---> INTERNEL ERROR");
			responseCodeStr =  "NOT FOUND/DOWN"; break;
		default: log.warn(url+" ---> UNKNOWN");
			responseCodeStr =  "UNKNOWN"; break;
		}	
		
		return responseCodeStr;
	}
	
	//Jede 15 Sekunden werden an allen Server-URLs Httpanfrage geschickt und die Antwort aus der Anfrage gespeichert
	@Scheduled(fixedRate = 15000)
	public void checkServerUrlAvailability() throws IOException {
		String responseCodeStr="";
		for(Url url:urls) {
			try {
				URL urlObj = new URL(url.getWebUrl().strip());
				HttpURLConnection huc = (HttpURLConnection) urlObj.openConnection();
				int responseCode = huc.getResponseCode();
				responseCodeStr = checkHttpStatusCode(responseCode, url.getWebUrl());
				url.setStatus(responseCodeStr);
			} catch (Exception e) {
				url.setStatus("FEHLER");
				log.warn("Exception occurred while sending Http Request with the Server-URL: "+ url.getWebUrl() +"!");
			}	
			urlRepo.save(url);
			
		}
		log.info("Scheduled Job for checking Availabilty Done!");
	}
	
	//Jede halbe Minute werden die Server-Url zum Testen manipuliert, damit die Erreichbarkeit auch aendert
	@Scheduled(fixedRate = 30000)
	public void manipulateUrlForCheck() throws IOException {
		Random random = new Random();
		for(Url url:urls) {
			if(random.nextBoolean()) {
				url.setWebUrl("https://www." + url.getName().toLowerCase() + ".de");
			}else {
				url.setWebUrl("https://www." + url.getName().toLowerCase() + ".com");
			}
			urlRepo.save(url);			
		}
		log.info("Scheduled Job for manipulation for test purpose done!");
	}

}

package juniq.probeaufgabe.monitoringApp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import juniq.probeaufgabe.monitoringApp.Repo.UrlRepo;
import juniq.probeaufgabe.monitoringApp.model.Url;

@RestController
//@RequestMapping("/url")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

	private UrlRepo urlRepo;
	
	public UrlController(UrlRepo urlRepo) {
		this.urlRepo = urlRepo;
	}
	
	@GetMapping("/urls")
	public List<Url> getAllUrls() {
		return (List<Url>) urlRepo.findAll();
	}
	
	@PostMapping("/add")
	public void addUrl(@RequestBody Url url) {
		urlRepo.save(url);
	}

}

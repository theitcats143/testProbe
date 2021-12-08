package juniq.probeaufgabe.monitoringApp.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import juniq.probeaufgabe.monitoringApp.model.Url;

@Repository
public interface UrlRepo extends CrudRepository<Url, Long> {

}

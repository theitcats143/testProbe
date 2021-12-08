package juniq.probeaufgabe.monitoringApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Url implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String serverIp;
	private String webUrl;
	private String status;

	
	public Url() {
	}

	public Url(String name, String serverIp, String webUrl, String status) {
		this.name = name;
		this.serverIp = serverIp;
		this.webUrl = webUrl;
		this.status =  status;
	}

	@Override
	public String toString() {
		return "Url [id=" + id + ", name=" + name + ", serverIp=" + serverIp + ", webUrl=" + webUrl + ", status="
				+ status + "]";
	}

	
}

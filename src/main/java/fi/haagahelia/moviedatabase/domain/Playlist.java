package fi.haagahelia.moviedatabase.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String release_date;
	@Column(length=1000)
	private String overview;
	
	
	
	public Playlist() {
		
	}



	public Playlist(String title, String release_date, String overview) {
		super();
		this.title = title;
		this.release_date = release_date;
		this.overview = overview;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getRelease_date() {
		return release_date;
	}



	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}



	public String getOverview() {
		return overview;
	}



	public void setOverview(String overview) {
		this.overview = overview;
	}
	
}

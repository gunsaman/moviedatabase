package fi.haagahelia.moviedatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class MovieList{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String release_date;
	@Column(length=1000)
	private String overview;
	
	
	public MovieList() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public MovieList(String title, String release_date, String overview) {
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


	@Override
	public String toString() {
		return " [id=" + id + ", title=" + title + ", vote_average="  + ", release_date="
				+ release_date + ", overview=" + overview + "]";
	}
	
	
	
	
}

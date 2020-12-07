package fi.haagahelia.moviedatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	@JoinColumn(name ="id")
	private Playlist list;	
	// default movie constructor without parameter
	public MovieList() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	// movie constructor using parameters
	public MovieList(String title, String release_date, String overview) {
		super();
		this.title = title;
		this.release_date = release_date;
		this.overview = overview;
	}


	public MovieList(String title, String release_date, String overview, Playlist list) {
		super();
		this.title = title;
		this.release_date = release_date;
		this.overview = overview;
		this.list = list;
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


	public Playlist getList() {
		return list;
	}


	public void setList(Playlist list) {
		this.list = list;
	}


	@Override
	public String toString() {
		return " [id=" + id + ", title=" + title + ", vote_average="  + ", release_date="
				+ release_date + ", overview=" + overview + "]";
	}
	
	
	
	
}

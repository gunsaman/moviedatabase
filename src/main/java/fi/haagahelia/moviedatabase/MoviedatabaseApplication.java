package fi.haagahelia.moviedatabase;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;


@SpringBootApplication
public class MoviedatabaseApplication {
	
	private static final Logger log = LoggerFactory.getLogger(MoviedatabaseApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MoviedatabaseApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, MovieListRepository moviesrepo) throws Exception {
		return args -> {
			String result = restTemplate.getForObject(
					"https://api.themoviedb.org/3/movie/popular?api_key=9cf94028fec53093dbf929b47de035b3", String.class);
			System.out.println("the length is " +result);
			
			JSONObject root = new JSONObject(result);
			
			JSONArray results = root.getJSONArray("results");
			
			for(int i=0; i < results.length(); i++ ) {
				JSONObject movieJson = results.getJSONObject(i);
				// movies that we will populate from json data fetched from api (String title, float vote_average, String release_date, String overview
				
				String title= movieJson.getString("title");
				String year= movieJson.getString("release_date");
				String overview = movieJson.getString("overview");
				moviesrepo.save(new MovieList(title, year,overview));
				
				
				
			}
			//moviesrepo.save(new MovieList(movie[0].getTitle(), movie[0].getVote_average(), movie[0].getRelease_date(), movie[0].getOverview()));
			
			log.info(result.toString());
		};
	}
}



package fi.haagahelia.moviedatabase;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import fi.haagahelia.moviedatabase.domain.LatestMovie;
import fi.haagahelia.moviedatabase.domain.LatestMovieRepository;
import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;
import fi.haagahelia.moviedatabase.domain.User;
import fi.haagahelia.moviedatabase.domain.UserRepository;


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
	@Autowired UserRepository uRepo;
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, MovieListRepository moviesrepo, LatestMovieRepository lrepo) throws Exception {
		return args -> {
			
			// save a global user to check application
			User user1 = new User("tester", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6","user","test@email.com");
			uRepo.save(user1);
			
			
			// saving the response from themoviedb api to a string variable
			String result = restTemplate.getForObject(
					"https://api.themoviedb.org/3/movie/popular?api_key=9cf94028fec53093dbf929b47de035b3", String.class);
			System.out.println("the length is " +result);
			
			// converting the response saved in the result string variable to JsonObject
			JSONObject root = new JSONObject(result);
			
			// 
			JSONArray results = root.getJSONArray("results");
			
			//populating the movielist object table from the response data collected from themoviedbapi
			for(int i=0; i < results.length(); i++ ) {
				JSONObject movieJson = results.getJSONObject(i);
				
				
				String title= movieJson.getString("title");
				String year= movieJson.getString("release_date");
				String overview = movieJson.getString("overview");
				moviesrepo.save(new MovieList(title, year,overview));
				
								
			}
			// saving the response of upcoming movie list from themoviedb api to a string variable
			String result1 = restTemplate.getForObject(
					"https://api.themoviedb.org/3/movie/upcoming?api_key=9cf94028fec53093dbf929b47de035b3", String.class);
			System.out.println("the length is " +result);
			
			JSONObject root1 = new JSONObject(result1);
			
			JSONArray results2 = root1.getJSONArray("results");

			//populating the upcoming movie table from the response data collected from themoviedbapi
			for(int i=0; i < results.length(); i++ ) {
				JSONObject movieJson = results2.getJSONObject(i);
					
				String title= movieJson.getString("title");
				String year= movieJson.getString("release_date");
				String overview = movieJson.getString("overview");
				lrepo.save(new LatestMovie(title, year,overview));
				
				
				
			}
			
			
			log.info(result.toString());
		};
	}
}



	package fi.haagahelia.moviedatabase;

	import static org.assertj.core.api.Assertions.assertThat;


	import java.util.List;

	import org.junit.jupiter.api.Test;
	import org.junit.runner.RunWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;

	

	@RunWith(SpringRunner.class)
	@SpringBootTest
	class MovieListRepoTest {

		@Autowired
		private MovieListRepository mRepo;
		
		@Test
		public void createNewBook() {
			MovieList movie = new MovieList("Earth2050", "2030","Human in earth are struggling to find a liveable planet");
			mRepo.save(movie);
			assertThat(movie.getId()).isNotNull();
		}
		
		@Test
		public void findMovie() {
			List<MovieList> movie = mRepo.findByTitle("Earth2050");
			
			assertThat(movie).hasSize(1);
			assertThat(movie.get(0).getTitle()).isEqualTo("Earth2050");
		}
		
		
	}




package fi.haagahelia.moviedatabase;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.moviedatabase.domain.LatestMovie;
import fi.haagahelia.moviedatabase.domain.LatestMovieRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class LatestMovieRepositoryTest {

	@Autowired
	private LatestMovieRepository lRepo;
	
	@Test
	public void createNewBook() {
		LatestMovie movie = new LatestMovie("Mars2050", "2030","1 Million human being migrate to mars.");
		lRepo.save(movie);
		assertThat(movie.getId()).isNotNull();
	}
	
	@Test
	public void findMovie() {
		List<LatestMovie> movie = lRepo.findByTitle("Mars2050");
		
		assertThat(movie).hasSize(1);
		assertThat(movie.get(0).getTitle()).isEqualTo("Mars2050");
	}
	
	
}

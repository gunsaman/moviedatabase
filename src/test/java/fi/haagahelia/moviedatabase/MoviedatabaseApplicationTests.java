package fi.haagahelia.moviedatabase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import fi.haagahelia.moviedatabase.web.MovieController;

@SpringBootTest
class MoviedatabaseApplicationTests {

	@Autowired
	private MovieController controller;
	
	@Test 
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}

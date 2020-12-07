package fi.haagahelia.moviedatabase.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LatestMovieRepository extends CrudRepository<LatestMovie, Long> {

	List<LatestMovie> findByTitle(String string);

}

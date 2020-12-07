package fi.haagahelia.moviedatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieListRepository extends JpaRepository<MovieList, Long> {

	List<MovieList> findByTitle(String string);

}

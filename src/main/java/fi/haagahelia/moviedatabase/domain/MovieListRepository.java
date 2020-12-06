package fi.haagahelia.moviedatabase.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieListRepository extends JpaRepository<MovieList, Long> {

}

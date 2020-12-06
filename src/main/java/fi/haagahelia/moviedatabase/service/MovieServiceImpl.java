package fi.haagahelia.moviedatabase.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;

public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieListRepository movieRepo;
	
	@Override
	public List<MovieList> getAllMovies() {
		return movieRepo.findAll();
	}

	
	public Page<MovieList> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo -1, pageSize);
		return this.movieRepo.findAll(pageable);
	}


	


	@Override
	public void deleteMovieListById(long id) {
		this.movieRepo.deleteById(id);
		
	}


	@Override
	public void saveMovie(MovieList movie) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public MovieList getMovieListById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

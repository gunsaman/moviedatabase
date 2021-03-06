package fi.haagahelia.moviedatabase.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import fi.haagahelia.moviedatabase.domain.LatestMovie;
import fi.haagahelia.moviedatabase.domain.LatestMovieRepository;
import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;
import fi.haagahelia.moviedatabase.domain.PlayListRepository;
import fi.haagahelia.moviedatabase.domain.Playlist;


@Controller
public class MovieController {
	
	@Autowired
	private MovieListRepository movies;
	@Autowired
	private PlayListRepository pmovies;
	@Autowired
	private LatestMovieRepository lmovies;
	
	
	 //RESTful service to get all the books:
    @RequestMapping(value="/upcoming-movies", method = RequestMethod.GET)
    public @ResponseBody List<LatestMovie> latestMovies(){
    	return (List<LatestMovie>) lmovies.findAll();
    }
    
    @RequestMapping(value="/latest-movies", method = RequestMethod.GET)
    public @ResponseBody List<MovieList> upcomingMovies(){
    	return (List<MovieList>) movies.findAll();
    }
    
    // RESTful service For searching book by Id
    @RequestMapping(value="/latest/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<LatestMovie> findlatestMovie(@PathVariable("id") Long id){
    	return lmovies.findById(id);
    }
	// adding movies to the model and passing it to the view layer.
	  @RequestMapping(value={"/movieList", "/"})
	  public String movieList(Model model) { 
		  
		  model.addAttribute("movielist", movies.findAll()) ; return
	  "movieList";
	  
	  }
	// adding latest movies to the model and passing it to the view layer.
	  @RequestMapping(value={"/latest"})
	  public String latest(Model model) { 
		  
		  model.addAttribute("movielist", lmovies.findAll()) ; 
		  return "latest";
	  
	  }
	  
	  // getting detail view of  a movie from 
	  @RequestMapping(value={"/details/{id}"}) 
	  public String movieDetails(@PathVariable("id") Long id, Model model) { 
		   model.addAttribute("movieDetail", movies.findById(id).get()) ; 
		  		   
		  return  "details";
	  
	  }
	  @RequestMapping(value={"/detail/{id}"}) 
	  public String movieDetail(@PathVariable("id") Long id, Model model) { 
		   model.addAttribute("movieDetail", lmovies.findById(id).get()) ; 
		  		   
		  return  "detail";
	  
	  }
	  
//	  @RequestMapping("/upcoming")
//		public String upcoming(RestTemplate restTemplate, Model model) {
//		  String result = restTemplate.getForObject(
//					"https://api.themoviedb.org/3/movie/popular?api_key=9cf94028fec53093dbf929b47de035b3", String.class);
//			System.out.println("the length is " +result);
//			
//			JSONObject root = new JSONObject(result);
//			
//			JSONArray results = root.getJSONArray("results");
//			model.addAttribute("playlist", pmovies.findAll()) ;
//			return "Playlist";
//			
//		}
	 
	
	/*
	 * @RequestMapping(value={"/movieList", "/"}) public String movieList(Model
	 * model) { return findPaginated(1, model);
	 * 
	 * }
	 */
	  
	  // displaying playlist created by user to the view
	@RequestMapping("/playlist")
	public String Playlist(Model model) {
		model.addAttribute("playlist", pmovies.findAll()) ;
		return "Playlist";
		
	}
	
	// add a new movie  by user if not found in the database
	@RequestMapping("/add")
	public String addMovie(Model model) {
		model.addAttribute("movie", new MovieList());
		return "addMovie";
		
	}
	
	
	  @RequestMapping(value="/login")
	    public String login() {	
	        return "login";
	    }	
	
	  // adding a movie to the playlist 
	@RequestMapping(value="/addPlaylist/{id}")
	public String addPlay(@PathVariable("id") Long id, Model model) {
		Optional<MovieList> result = movies.findById(id);
		// check if data is present
		if(result.isPresent()) {
			// get data from result to playlist entity
			MovieList favouriteMovie = result.get();
			
			pmovies.save(new Playlist(favouriteMovie.getTitle(), favouriteMovie.getRelease_date(), favouriteMovie.getOverview()));
			}				
		
		return "redirect:../movieList";
		
	}
	@RequestMapping(value="/addPlaylists/{id}")
	public String addPlay2(@PathVariable("id") Long id, Model model) {
		Optional<LatestMovie> result = lmovies.findById(id);
		// check if data is present
		if(result.isPresent()) {
			// get data from result to playlist entity
			LatestMovie favouriteMovie = result.get();
			
			pmovies.save(new Playlist(favouriteMovie.getTitle(), favouriteMovie.getRelease_date(), favouriteMovie.getOverview()));
			}				
		
		return "redirect:../latest";
		
	}
	
	 // save a new movie
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MovieList movie){
        movies.save(movie);
        return "redirect:movieList";
    }  
    // deleted a movie 
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long id, Model model) {
    	pmovies.deleteById(id);
        return "redirect:../playlist";
    }    
    
    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String resetPwd(Model model) {
    	
        return "reset_password_form";
    }    
	/*
	 * //handling pagination
	 * 
	 * @RequestMapping("/page/{pageNo}") public String findPaginated(@PathVariable
	 * (value="pageNo") int pageNo, Model model) { int pageSize=10; Page<MovieList>
	 * page = MovieService.findPaginated(pageNo, pageSize); List<MovieList>
	 * movieList = page.getContent(); model.addAttribute("currentPage",pageNo);
	 * model.addAttribute("totalPages", page.getTotalPages());
	 * model.addAttribute("totalItems", page.getTotalElements());
	 * model.addAttribute("listOfMovies", movieList); return "movieList"; }
	 */
}

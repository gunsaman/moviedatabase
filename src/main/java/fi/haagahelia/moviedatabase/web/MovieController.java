package fi.haagahelia.moviedatabase.web;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.MovieListRepository;
import fi.haagahelia.moviedatabase.domain.PlayListRepository;
import fi.haagahelia.moviedatabase.domain.Playlist;
import fi.haagahelia.moviedatabase.service.MovieService;

@Controller
public class MovieController {
	
	@Autowired
	private MovieListRepository movies;
	@Autowired
	private PlayListRepository pmovies;
	
	
	  @RequestMapping(value={"/movieList", "/"})
	  public String movieList(Model model) { 
		  
		  model.addAttribute("movielist", movies.findAll()) ; return
	  "movieList";
	  
	  }
	  @RequestMapping(value={"/details/{id}"}) 
	  public String movieDetails(@PathVariable("id") Long id, Model model) { 
		   model.addAttribute("movieDetail", movies.findById(id).get()) ; 
		   
		  return  "details";
	  
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
	@RequestMapping("/playlist")
	public String Playlist(Model model) {
		model.addAttribute("playlist", pmovies.findAll()) ;
		return "Playlist";
		
	}
	@RequestMapping("/add")
	public String addMovie(Model model) {
		model.addAttribute("movie", new MovieList());
		return "addMovie";
		
	}
	  @RequestMapping(value="/login")
	    public String login() {	
	        return "login";
	    }	
	
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
	
	 //save book
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MovieList movie){
        movies.save(movie);
        return "redirect:movieList";
    }  
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long id, Model model) {
    	pmovies.deleteById(id);
        return "redirect:../playlist";
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

package com.mongoapps.movie.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongoapps.movie.model.Movie;
import com.mongoapps.movie.service.MovieService;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}

	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable Long id) {
		return movieService.getMovieById(id);
	}
	
	@GetMapping("/api/movies/{movieId}/seats")
	public ResponseEntity<Map<String, Object>> getMovieSeatStatus(@PathVariable Long movieId) {
	    Map<String, Object> response = movieService.getMovieSeatStatus(movieId);
	    return ResponseEntity.ok(response);
	}

}
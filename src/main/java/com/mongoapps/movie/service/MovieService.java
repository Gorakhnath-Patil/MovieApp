package com.mongoapps.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongoapps.movie.exception.ResourceNotFoundException;
import com.mongoapps.movie.model.Booking;
import com.mongoapps.movie.model.Movie;
import com.mongoapps.movie.repository.BookingRepository;
import com.mongoapps.movie.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private final MovieRepository movieRepository;

	@Autowired

	private final BookingRepository bookingRepository;

	public MovieService(MovieRepository movieRepository, BookingRepository bookingRepository) {
        this.movieRepository = movieRepository;
        this.bookingRepository = bookingRepository;
    }

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public Movie getMovieById(Long id) {
		return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
	}

	public Movie updateMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public Map<String, Object> getMovieSeatStatus(Long movieId) {
		// Fetch the movie details
		Movie movie = movieRepository.findById(movieId)
		        .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));


		// Fetch all bookings for the movie
		List<Booking> bookings = bookingRepository.findByMovieId(movieId);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("movieId", movie.getId());
		response.put("title", movie.getTitle());
		response.put("showTiming", movie.getShowTiming());
		response.put("availableSeats", movie.getAvailableSeats());

		// Map booked seats
		List<Map<String, String>> bookedSeats = bookings.stream().map(booking -> {
			Map<String, String> seatInfo = new HashMap<>();
			seatInfo.put("seatNumber", booking.getSeatNumber());
			seatInfo.put("customerName", booking.getCustomerName());
			return seatInfo;
		}).collect(Collectors.toList());

		response.put("bookedSeats", bookedSeats);
		return response;
	}

}
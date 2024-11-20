package com.mongoapps.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongoapps.movie.model.Booking;
import com.mongoapps.movie.model.Movie;
import com.mongoapps.movie.repository.BookingRepository;
import com.mongoapps.movie.repository.MovieRepository;

@Service
public class BookingService {
	private final BookingRepository bookingRepository;
	private final MovieRepository movieRepository;

	public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository) {
		this.bookingRepository = bookingRepository;
		this.movieRepository = movieRepository;
	}

	public List<Booking> getBookingsByMovieId(Long movieId) {
		return bookingRepository.findByMovieId(movieId);
	}

	public Booking bookTicket(Long movieId, String customerName) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
		if (movie.getAvailableSeats() <= 0) {
			throw new RuntimeException("No seats available");
		}

		String seatNumber = "Seat-" + (101 - movie.getAvailableSeats());
		movie.setAvailableSeats(movie.getAvailableSeats() - 1);
		movieRepository.save(movie);

		Booking booking = new Booking();
		booking.setMovieId(movieId);
		booking.setCustomerName(customerName);
		booking.setSeatNumber(seatNumber);
		return bookingRepository.save(booking);
	}

	public void cancelTicket(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));
		Movie movie = movieRepository.findById(booking.getMovieId())
				.orElseThrow(() -> new RuntimeException("Movie not found"));

		movie.setAvailableSeats(movie.getAvailableSeats() + 1);
		movieRepository.save(movie);
		bookingRepository.deleteById(bookingId);
	}
}

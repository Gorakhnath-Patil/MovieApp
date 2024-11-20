package com.mongoapps.movie.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongoapps.movie.model.Booking;
import com.mongoapps.movie.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/movie/{movieId}")
	public List<Booking> getBookingsByMovie(@PathVariable Long movieId) {
		return bookingService.getBookingsByMovieId(movieId);
	}

	@PostMapping("/{movieId}")
	public Booking bookTicket(@PathVariable Long movieId, @RequestParam String customerName) {
		return bookingService.bookTicket(movieId, customerName);
	}

	@DeleteMapping("/{bookingId}")
	public void cancelTicket(@PathVariable Long bookingId) {
		bookingService.cancelTicket(bookingId);
	}
}
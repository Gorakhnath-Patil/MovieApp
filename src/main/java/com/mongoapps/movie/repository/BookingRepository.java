package com.mongoapps.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mongoapps.movie.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByMovieId(Long movieId);
}
package com.mongoapps.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mongoapps.movie.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
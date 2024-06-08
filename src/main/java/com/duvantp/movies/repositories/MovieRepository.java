package com.duvantp.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duvantp.movies.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> { }

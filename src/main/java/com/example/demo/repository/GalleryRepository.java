package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

	

	Optional<Gallery> findByUsername(String username);

	


}

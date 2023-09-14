package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

	@Query("SELECT g FROM Gallery g WHERE LOWER(g.galleryname) LIKE LOWER(CONCAT('%', :galleryname, '%'))")
	List<Gallery> findGalleryByName(@Param("galleryname") String name);

	Optional<Gallery> findByUsername(String username);

	


}

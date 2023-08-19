package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

	@Query("SELECT g FROM Gallery g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Gallery> findGalleryByName(@Param("name") String name);


}

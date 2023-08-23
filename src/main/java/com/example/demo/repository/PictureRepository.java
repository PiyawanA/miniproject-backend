package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long>{

	@Query("SELECT p.picId, p.name, p.caption, p.time FROM Picture p WHERE p.gallery.galleryId = :galleryId")
	List<Object[]> findPictureByGalleryId(@Param("galleryId") Long galleryId);

}

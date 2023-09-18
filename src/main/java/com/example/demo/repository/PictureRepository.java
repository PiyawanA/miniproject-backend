package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long>{

	@Query("SELECT p.picId, p.name, p.caption, p.time , p.pic, p.gallery FROM Picture p WHERE p.gallery.galleryId = :galleryId")
	List<Object[]> findPictureByGalleryId(@Param("galleryId") Long galleryId);

	
	@Query("SELECT p FROM Picture p WHERE LOWER(p.gallery.galleryname) LIKE LOWER(CONCAT('%', :galleryname, '%'))")
	List<Picture> findGalleryByName(@Param("galleryname") String galleryname);




}

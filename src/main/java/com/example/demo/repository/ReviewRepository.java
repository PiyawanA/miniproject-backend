package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r.reviewId, r.comment, r.time, r.member  FROM Review r WHERE r.picture.picId = :picId")
	List<Object[]> findReviewByPicId(@Param("picId") Long picId);

}

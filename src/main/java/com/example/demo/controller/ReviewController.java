package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Gallery;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewController {

	@Autowired
	ReviewRepository reviewRepository;

	@GetMapping("/review/{picId}")
	public ResponseEntity<Object> getReviewByPicId(@PathVariable("picId") Long picId) {

		try {

			List<Object[]> listReviews = reviewRepository.findReviewByPicId(picId);
			List<Review> reviews = new ArrayList<>();

			for (Object[] row : listReviews) {
				Long reviewId = (Long) row[0];
				String comment = (String) row[1];
				Timestamp time = (Timestamp) row[2];
				Gallery gallery = (Gallery) row[3];
				

				gallery.setPassword(null);

				Review review = new Review(reviewId, comment, time, gallery, null);

				reviews.add(review);
			}

			return new ResponseEntity<>(reviews, HttpStatus.OK);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/review")
	public ResponseEntity<Object> addView(@RequestBody Review body) {
		try {

			Timestamp now = new Timestamp(System.currentTimeMillis());
			body.setTime(now);

			Review review = reviewRepository.save(body);

			return new ResponseEntity<>(review, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/review/{reviewId}")
	public ResponseEntity<Object> editReview(@PathVariable("reviewId") Long reviewId, @RequestBody Review body) {

		try {

			Optional<Review> reviewFind = reviewRepository.findById(reviewId);

			if (reviewFind.isPresent()) {

				Timestamp now = new Timestamp(System.currentTimeMillis());

				Review reviewEdit = reviewFind.get();

				reviewEdit.setComment(body.getComment());
				reviewEdit.setTime(now);

				reviewRepository.save(reviewEdit);

				return new ResponseEntity<>(reviewEdit, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Review Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<Object> deleteReview(@PathVariable Long reviewId) {

		try {
			Optional<Review> review = reviewRepository.findById(reviewId);

			if (review.isPresent()) {

				reviewRepository.delete(review.get());

				return new ResponseEntity<>("Delete Sucess", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

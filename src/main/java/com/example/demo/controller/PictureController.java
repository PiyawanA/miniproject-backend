package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Gallery;
import com.example.demo.model.Picture;
import com.example.demo.model.Review;
import com.example.demo.repository.PictureRepository;
import com.example.demo.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PictureController {

	@Autowired
	PictureRepository pictureRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/photo")
	public ResponseEntity<Object> getMember() {
		try {

			List<Picture> pictures = pictureRepository.findAll();
			
			return new ResponseEntity<>(pictures, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/photo", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> postPicture(@RequestParam("body") String body,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		try {
			Picture picture = new ObjectMapper().readValue(body, Picture.class);
			picture.setPic(photo.getBytes());
			Timestamp now = new Timestamp(System.currentTimeMillis());
			picture.setTime(now);

			Picture newPicture = pictureRepository.save(picture);
			return new ResponseEntity<>(newPicture, HttpStatus.CREATED);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error processing the photo.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/photo/{galleryId}")
	public ResponseEntity<Object> getPhotoBygalleryId(@PathVariable("galleryId") Long galleryId) {

		try {

			  List<Object[]> listPhoto = pictureRepository.findPictureByGalleryId(galleryId);
		        List<Picture> pictures = new ArrayList<>();

		        for (Object[] row : listPhoto) {
		            Long picId = (Long) row[0];
		            String name = (String) row[1];
		            String caption = (String) row[2];
		            Timestamp time = (Timestamp) row[3];
		            byte[] pic = (byte[]) row[4];
		            Gallery gallery = (Gallery) row[5];

		            Picture picture = new Picture(picId, name, caption, time, pic, gallery);
		            pictures.add(picture);
		        }

		        return new ResponseEntity<>(pictures, HttpStatus.OK);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/photo/{picId}")
	public ResponseEntity<Object> deleteBoardGameById(@PathVariable("picId") Long picId) {

		try {

			Optional<Picture> picFound = pictureRepository.findById(picId);

			if (picFound.isPresent()) {

				List<Object[]> listReviews = reviewRepository.findReviewByPicId(picId);

				for (Object[] row : listReviews) {
					Long reviewId = (Long) row[0];
					String comment = (String) row[1];
					Timestamp time = (Timestamp) row[2];
					Gallery gallery = (Gallery) row[3];

					Review review = new Review(reviewId, comment, time, gallery, null);

					reviewRepository.delete(review);

				}

				pictureRepository.delete(picFound.get());

				return new ResponseEntity<>("Delete Success.", HttpStatus.OK);

			} else {
				return new ResponseEntity<>(" Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/searchGallery")
	public ResponseEntity<Object> searchGallery(@RequestParam("galleryname") String galleryname) {

		try {
			List<Picture> galleryFound = pictureRepository.findGalleryByName(galleryname);

			if (!galleryFound.isEmpty()) {
				return new ResponseEntity<>(galleryFound, HttpStatus.OK);

			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	}



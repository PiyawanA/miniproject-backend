package com.example.demo.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Picture;
import com.example.demo.repository.PictureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PictureController {

	@Autowired
	PictureRepository pictureRepository;

	@PostMapping(value = "/photo", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> postPicture(@RequestParam("body") String body,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		try {
			Picture picture = new ObjectMapper().readValue(body, Picture.class);
			String photoName = UUID.randomUUID().toString() + ".png";
			picture.setName(photoName);
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
	
	@DeleteMapping("/photo/{picId}")
	public ResponseEntity<Object> deletePictuResponseEntity (@PathVariable Long picId){
		try {
			Optional<Picture> picture = pictureRepository.findById(picId);

			if (picture.isPresent()) {

				pictureRepository.delete(picture.get());

				return new ResponseEntity<>("Delete Sucess", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

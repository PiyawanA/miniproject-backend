package com.example.demo.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Gallery;
import com.example.demo.repository.GalleryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GalleryController {

	@Autowired
	GalleryRepository galleryRepository;

	@PostMapping(value = "/gallery", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> postPicture(@RequestParam("body") String body,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		try {
			Gallery gallery = new ObjectMapper().readValue(body, Gallery.class);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			gallery.setPicprofile(photo.getBytes());
			gallery.setTime(now);
		

			Gallery newGallery = galleryRepository.save(gallery);
			return new ResponseEntity<>(newGallery, HttpStatus.CREATED);

		} catch (IOException e) {

			e.printStackTrace();
			return new ResponseEntity<>("Error processing the photo.", HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
	
	@GetMapping("/searchGallery")
	public ResponseEntity<Object> searchGallery(@RequestParam("name") String name) {

		try {
			List<Gallery> galleryFound = galleryRepository.findGalleryByName(name);

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

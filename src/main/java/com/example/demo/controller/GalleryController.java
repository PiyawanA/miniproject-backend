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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Gallery;
import com.example.demo.model.Picture;
import com.example.demo.repository.GalleryRepository;
import com.example.demo.repository.PictureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GalleryController {

	@Autowired
	GalleryRepository galleryRepository;

	@Autowired
	PictureRepository pictureRepository;

	@GetMapping("/gallery")
	public ResponseEntity<Object> getGallery() {
		try {

			List<Gallery> gallerys = galleryRepository.findAll();
			return new ResponseEntity<>(gallerys, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/gallery", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> createGallery(@RequestParam("body") String body,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		try {
			Gallery gallery = new ObjectMapper().readValue(body, Gallery.class);

			gallery.setPicprofile(photo.getBytes());

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


	@PutMapping(value = "/gallery/{galleryId}", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> updateGallery(@PathVariable("galleryId") Long galleryId,
			@RequestParam("body") String body, @RequestParam("photo") MultipartFile photo) throws IOException {

		try {

			Optional<Gallery> galleryFound = galleryRepository.findById(galleryId);

			if (galleryFound.isPresent()) {

				Gallery gallery = new ObjectMapper().readValue(body, Gallery.class);

				Gallery galleryEdit = galleryFound.get();

				if (!photo.isEmpty()) {
					gallery.setPicprofile(photo.getBytes());

				}else {
					gallery.setPicprofile(galleryEdit.getPicprofile());
				}

				galleryEdit.setGalleryname(gallery.getGalleryname());
				galleryEdit.setDescription(gallery.getDescription());
				galleryEdit.setPicprofile(gallery.getPicprofile());
				
				galleryEdit.setFirstname(gallery.getFirstname());
				galleryEdit.setLastname(gallery.getLastname());
				galleryEdit.setEmail(gallery.getEmail());
				galleryEdit.setUsername(gallery.getUsername());
//				galleryEdit.setPassword(gallery.getPassword());
				
				galleryRepository.save(galleryEdit);

				return new ResponseEntity<>(galleryEdit, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Board Game Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/gallery/{galleryId}")
	public ResponseEntity<Object> deleteGallery(@PathVariable("galleryId") Long galleryId) {

		try {

			Optional<Gallery> galleryFound = galleryRepository.findById(galleryId);

			if (galleryFound.isPresent()) {

				List<Object[]> listPictures = pictureRepository.findPictureByGalleryId(galleryId);

				for (Object[] row : listPictures) {
					Long picId = (Long) row[0];
					String name = (String) row[1];
					String caption = (String) row[2];
					Timestamp time = (Timestamp) row[3];
					byte[] pic = (byte[]) row[4];

					Picture picture = new Picture(picId, name, caption, time, pic, null);

					pictureRepository.delete(picture);

				}

				galleryRepository.delete(galleryFound.get());

				return new ResponseEntity<>("Delete Board Game Success.", HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Board Game Not Found.", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping(value = "/register", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> registerUser(@RequestParam("body") String body){
		try {
			Gallery gallery = new ObjectMapper().readValue(body, Gallery.class);

			if (galleryRepository.findByUsername(gallery.getUsername()).isPresent()) {
				return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
			}

				gallery.setPicprofile(null);
			

			Timestamp now = new Timestamp(System.currentTimeMillis());
			gallery.setTime(now);

			Gallery savedMember = galleryRepository.save(gallery);
			return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody Gallery body) {
		try {

			Optional<Gallery> memberFound = galleryRepository.findByUsername(body.getUsername());

			if (memberFound.isPresent() && memberFound.get().getPassword().equals(body.getPassword())) {

				memberFound.get().setPassword(null);
				return new ResponseEntity<>(memberFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/checkUsername")
	public ResponseEntity<Object> checkUsername(@RequestParam("username") String username) {

		try {
			Optional<Gallery> userFound = galleryRepository.findByUsername(username);

			if (userFound.isPresent()) {
				return new ResponseEntity<>(true, HttpStatus.OK);

			} else {
				return new ResponseEntity<>(false, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

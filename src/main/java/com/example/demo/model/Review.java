package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reviewId;
	private String comment;
	private Timestamp time;

	@ManyToOne
	@JoinColumn(name = "galleryId")
	private Gallery gallery;

	@ManyToOne
	@JoinColumn(name = "picId")
	private Picture picture;
	


	public Review() {
		super();
	}

	public Review(Long reviewId, String comment, Timestamp time, Gallery gallery, Picture picture) {
		super();
		this.reviewId = reviewId;
		this.comment = comment;
		this.time = time;
		this.gallery = gallery;
		this.picture = picture;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}

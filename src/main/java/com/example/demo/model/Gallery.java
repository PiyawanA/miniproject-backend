package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "gellery")
public class Gallery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long galleryId;
	private String name;
	private String description;
	private Timestamp time;

	@Lob
	@Column(length = 3048576)
	private byte[] picprofile;

	@OneToMany
	@JoinColumn(name = "galleryId")
	private List<Picture> pictures;

	public Gallery() {
		super();
	}

	public Gallery(Long galleryId, String name, String description, Timestamp time, byte[] picprofile,
			List<Picture> pictures) {
		super();
		this.galleryId = galleryId;
		this.name = name;
		this.description = description;
		this.time = time;
		this.picprofile = picprofile;
		this.pictures = pictures;
	}

	public Long getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(Long galleryId) {
		this.galleryId = galleryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public byte[] getPicprofile() {
		return picprofile;
	}

	public void setPicprofile(byte[] picprofile) {
		this.picprofile = picprofile;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	

}

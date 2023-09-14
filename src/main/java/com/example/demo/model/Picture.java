package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "picture")
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long picId;
	private String name;
	private String caption;
	private Timestamp time;

	@Lob
	@Column(length = 3048576)
	private byte[] pic;

	@ManyToOne
	@JoinColumn(name = "galleryId")
	private Gallery gallery;

	public Picture() {
		super();
	}

	public Picture(Long picId, String name, String caption, Timestamp time, byte[] pic, Gallery gallery) {
		super();
		this.picId = picId;
		this.name = name;
		this.caption = caption;
		this.time = time;
		this.pic = pic;
		this.gallery = gallery;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

}

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
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	private String galleryname;
	private String description;
	private Timestamp time;

	@Lob
	@Column(length = 3048576)
	private byte[] picprofile;

//	@OneToMany
//	@JoinColumn(name = "galleryId")
//	private List<Picture> pictures;

	public Gallery() {
		super();
	}

	public Gallery(Long galleryId, String firstname, String lastname, String email, String username, String password,
			String galleryname, String description, Timestamp time, byte[] picprofile) {
		super();
		this.galleryId = galleryId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.galleryname = galleryname;
		this.description = description;
		this.time = time;
		this.picprofile = picprofile;
	}

	public Long getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(Long galleryId) {
		this.galleryId = galleryId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGalleryname() {
		return galleryname;
	}

	public void setGalleryname(String galleryname) {
		this.galleryname = galleryname;
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

}

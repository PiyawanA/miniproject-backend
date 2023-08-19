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
	private Integer reviewId;
	private String comment;
	private Timestamp time;

	@ManyToOne
	@JoinColumn(name = "memId")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "picId")
	private Picture picture;

	public Review() {
		super();
	}

	public Review(Integer reviewId, String comment, Timestamp time, Member member, Picture picture) {
		super();
		this.reviewId = reviewId;
		this.comment = comment;
		this.time = time;
		this.member = member;
		this.picture = picture;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

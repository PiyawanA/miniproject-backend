package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.ReviewRepository;

public class ReviewController {

	@Autowired
	ReviewRepository reviewRepository;
}

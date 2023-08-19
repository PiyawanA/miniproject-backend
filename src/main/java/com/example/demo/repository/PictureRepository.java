package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long>{

}

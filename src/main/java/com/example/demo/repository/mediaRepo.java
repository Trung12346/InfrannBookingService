package com.example.demo.repository;

import com.example.demo.model.media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface mediaRepo extends JpaRepository<media,Integer> {



}

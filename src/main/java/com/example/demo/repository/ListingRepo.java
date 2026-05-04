package com.example.demo.repository;

import com.example.demo.model.listings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface listingRepo extends JpaRepository<listings,Integer> {

    @Query("SELECT l FROM listings l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<listings> filterByName(@Param("keyword") String keyword, Pageable pageable);

}

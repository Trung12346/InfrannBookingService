package com.example.demo.repository;

import com.example.demo.model.BusinessProfile;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Integer> {
    public BusinessProfile findByUser(User user);
}

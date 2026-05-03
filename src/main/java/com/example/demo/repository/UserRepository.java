package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String name);

    public boolean existsByEmail(String email);

    public User findByEmail(String email);

    @Query(nativeQuery = true, value = """
        SELECT * FROM users WHERE (role & 2) != 0
    """)
    public List<User> findAllEmployees();
}

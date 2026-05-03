package com.example.demo.controller;

import com.example.demo.DTO.BusinessProfileAPI_get_0;
import com.example.demo.DTO.BusinessProfileAPI_put_0;
import com.example.demo.config.SecurityConfig;
import com.example.demo.model.BusinessProfile;
import com.example.demo.model.User;
import com.example.demo.repository.BusinessProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SessionPool;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BusinessProfileAPIController {
    @Autowired
    SessionPool sessionPool;
    @Autowired
    BusinessProfileRepository businessProfileRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/api/business/users/me") //-ro:b -e
    public ResponseEntity get_0() {
        BusinessProfile businessProfile = businessProfileRepository.findByUser(sessionPool.getUser());
        User user = businessProfile.getUser();

        BusinessProfileAPI_get_0 DTO = new BusinessProfileAPI_get_0(user, businessProfile);

        return ResponseEntity.ok(DTO);
    }

    @PostMapping("/api/business/users") //-a -c -e
    public ResponseEntity post_0() {
        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setUser(sessionPool.getUser());

        businessProfileRepository.save(businessProfile);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/business/users/me") //-ro:b
    public ResponseEntity put_0(
            @ModelAttribute BusinessProfileAPI_put_0 DTO
            ) {
        BusinessProfile businessProfile = businessProfileRepository.findByUser(sessionPool.getUser());
        businessProfile.setDisplayName(DTO.getDisplayName());
        businessProfile.setPhone(DTO.getPhone());

        businessProfileRepository.save(businessProfile);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/business/users/me/password") //-ro:b
    public ResponseEntity patch_0(
            @RequestParam("password") String password
    ) {
        User user = sessionPool.getUser();

        userService.changePassword(user, password, password);

        return ResponseEntity.ok().build();
    }

    //@PatchMapping("/api/business/users/me/avatar") //-a -ro:b

    @DeleteMapping("/api/business/users/me") //-ro:b
    public ResponseEntity delete_0() {
        User user = sessionPool.getUser();
        user.setEnabled(false);

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}

package com.example.demo.controller;

import com.example.demo.model.BusinessProfile;
import com.example.demo.model.VerificationRequest;
import com.example.demo.repository.BusinessProfileRepository;
import com.example.demo.repository.VerificationRequestRepository;
import com.example.demo.service.BusinessVerificationService;
import com.example.demo.service.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class BusinessVerificationAPIController {
    @Autowired
    BusinessVerificationService businessVerificationService;
    @Autowired
    VerificationRequestRepository verificationRequestRepository;
    @Autowired
    SessionPool sessionPool;
    @Autowired
    BusinessProfileRepository businessProfileRepository;

    @PostMapping("api/business/verifications/identity") //-ro -a
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity post_0(
            @RequestParam("id") Integer id,
            @RequestParam("id-front") MultipartFile IDFront,
            @RequestParam("id-back") MultipartFile IDBack
            ) {
        ArrayList<MultipartFile> multipartFiles = new ArrayList();
        multipartFiles.add(IDFront);
        multipartFiles.add(IDBack);

        businessVerificationService.sendRequest(multipartFiles.toArray(new MultipartFile[2]));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/moderation/verifications/identity/{id}") //-e -a
    public ResponseEntity get_0(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(verificationRequestRepository.findById(id).get());
    }

    @PatchMapping("/api/moderation/verifications/identity/{id}") //-e -a
    public ResponseEntity patch_0(@PathVariable("id") Integer id) {
        VerificationRequest verificationRequest = verificationRequestRepository.findById(id).get();
        verificationRequest.setStatus(1);
        verificationRequest.setReviewer(sessionPool.getUser());
        verificationRequest.setCreatedAt(LocalDateTime.now());
        verificationRequestRepository.save(verificationRequest);

        BusinessProfile businessProfile = businessProfileRepository.findByUser(verificationRequest.getRequester());
        businessProfile.setKYCVerification(true);
        businessProfileRepository.save(businessProfile);


        return ResponseEntity.ok().build();
    }

    @GetMapping("api/moderation/verifications/pending") //-e -a
    public ResponseEntity get_1() {
        return ResponseEntity.ok(verificationRequestRepository.findAll());
    }
}

package com.example.demo.service;

import com.example.demo.model.Media;
import com.example.demo.model.VerificationMedia;
import com.example.demo.model.VerificationRequest;
import com.example.demo.repository.MediaRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationMediaRepository;
import com.example.demo.repository.VerificationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BusinessVerificationService {
    @Autowired
    VerificationMediaRepository verificationMediaRepository;
    @Autowired
    VerificationRequestRepository verificationRequestRepository;
    @Autowired
    MediaService mediaService;
    @Autowired
    SessionPool sessionPool;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;

    public boolean sendRequest(MultipartFile[] documents) {
        VerificationMedia verificationMedia;
        VerificationRequest verificationRequest = new VerificationRequest();
        verificationRequest.setStatus(0);
        verificationRequest.setRequester(sessionPool.getUser());

        VerificationRequest request = verificationRequestRepository.save(verificationRequest);

        try {
            for(MultipartFile document: documents) {
                Media media = mediaService.save(document, String.format("/users/%d/verification/id", sessionPool.getUser().getId()));

                verificationMedia = new VerificationMedia();
                verificationMedia.setMedia(media);
                verificationMedia.setVerificationRequest(request);

                verificationMediaRepository.save(verificationMedia);
            }
        } catch (IOException exception) {
            System.err.println(exception);
            return false;
        }
        return true;
    }
}

package com.example.demo.service;

import com.example.demo.core.Encoder;
import com.example.demo.core.HashingAlgos;
import com.example.demo.enumRole.Role;
import com.example.demo.model.Media;
import com.example.demo.repository.MediaRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class MediaService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    SessionPool sessionPool;

    public Media save(MultipartFile multipartFile, String URLPrefix) throws IOException {
        Media media = new Media();

        String name = multipartFile.getName();
        String fileType = multipartFile.getContentType();

        //generating unique name
        byte[] binName;
        byte[] binFileType;
        byte[] binData = multipartFile.getBytes();


        Encoder encoder = new Encoder();
        encoder.setInput(name);
        encoder.setInputType(Encoder.UTF8);
        encoder.normalize();
        binName = encoder.getMedium();

        encoder = new Encoder();
        encoder.setInput(fileType);
        encoder.setInputType(Encoder.UTF8);
        encoder.normalize();
        binFileType = encoder.getMedium();

        byte[] binMedia;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(binData);
        byteArrayOutputStream.write(binName);
        byteArrayOutputStream.write(binFileType);
        binMedia = byteArrayOutputStream.toByteArray();

        byte[] hashMedia = HashingAlgos.MD5(binMedia);

        encoder = new Encoder();
        encoder.setMedium(hashMedia);
        encoder.setOutputType(Encoder.HEX);
        encoder.encode();
        String fileName = encoder.oHEX;

        //setting params
        media.setMedia(binData);
        media.setFileType(fileType);
        media.setURL(String.format("%s/%s", URLPrefix, fileName));
        media.setOwner(sessionPool.getUser());
        media.setStatus(0);
        media.setPermission(Role.EMPLOYEE.getValue() + Role.ADMIN.getValue());
        return mediaRepository.save(media);

    }
}

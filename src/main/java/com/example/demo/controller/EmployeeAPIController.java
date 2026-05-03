package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeAPIController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RegisterService registerService;

    @PostMapping("/api/employees") //-a
    public ResponseEntity post_0(
            @ModelAttribute("user") User user,
            @RequestParam("password") String password
            ) {
        registerService.register(user, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/employees") //-a
    public ResponseEntity get_0() {
        return ResponseEntity.ok(userRepository.findAllEmployees());
    }

    @GetMapping("/api/employees/{id}") //-a -ro
    public ResponseEntity get_1(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(userRepository.findById(id).get());
    }

//    @PutMapping("/api/employees/{id}") //-a
//    public ResponseEntity put_0(
//            @PathVariable("id") Integer id
//    ) {
//        User user = userRepository.findById(id).get();
//        user.setDeleted(true);
//        userRepository.save(user);
//
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("/api/employees/{id}") //-a
    public ResponseEntity delete_0(
            @PathVariable("id") Integer id
    ) {
        User user = userRepository.findById(id).get();
        user.setDeleted(true);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

//    @PatchMapping("/api/employees/{id}/status") //-a
//    public ResponseEntity patch_0(
//            @PathVariable("id") Integer id
//    ) {
//
//        return ResponseEntity.ok().build();
//    }

}

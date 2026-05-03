package com.example.demo.service;

import com.example.demo.model.UserDetail;
import com.example.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionPool {
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetail) authentication.getPrincipal()).getUser();
    }

}

package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    UsersDetailsService usersDetailsService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/Admin")
    public String Admin(Model model, Authentication authentication) throws ServletException, IOException {
            Users user = null; 
            if(authentication instanceof OAuth2AuthenticationToken){
                OAuth2AuthenticationToken oauth2token = (OAuth2AuthenticationToken) authentication;
                OAuth2User oAuth2User = oauth2token.getPrincipal();
                String email = oAuth2User.getAttribute("email");
                user = userRepository.findByEmail(email);
            }else if(authentication instanceof UsernamePasswordAuthenticationToken){
                String username = authentication.getName();
                user = userRepository.findByUsername(username);
            }
            if(user == null){
                return "redirect:/Login?error=true";
            }
            model.addAttribute("User",user.getUsername());
            model.addAttribute("id",user.getId());
        return "admin";
    }
}

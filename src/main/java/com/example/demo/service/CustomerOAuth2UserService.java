package com.example.demo.service;

import com.example.demo.enumRole.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerOAuth2UserService extends OidcUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
       OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getAttribute("email");

        Users users = userRepository.findByEmail(email);
        if(users == null){
            throw new OAuth2AuthenticationException("User not found with that email");
        }
        List<GrantedAuthority> listRole = new ArrayList<>(oidcUser.getAuthorities());
        for(Role r: Role.values()){
            if((users.getRole() & r.getValue()) !=0){
                listRole.add(new SimpleGrantedAuthority("ROLE_"+r.name()));

            }
        }
        return new DefaultOidcUser(listRole,oidcUser.getIdToken(),oidcUser.getUserInfo());
        }

}

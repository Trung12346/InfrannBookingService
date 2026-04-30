package demo.demo.Service;

import demo.demo.Entity.Users;
import demo.demo.EnumRole.Role;
import demo.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.*;
import java.util.Map;
@Service
public class CustomerOauth2UserService extends OidcUserService {

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

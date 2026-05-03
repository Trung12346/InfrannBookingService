package com.example.demo.model;

import com.example.demo.enumRole.Role;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {
    private User user;

    public User getUser() {
        return user;
    }

    public UserDetail(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(Role r : Role.values()){
            if((user.getRole() & r.getValue()) !=0){
                authorityList.add(new SimpleGrantedAuthority("ROLE_"+r.name()));
            }
        }
        return authorityList;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword_hash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

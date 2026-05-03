package com.example.demo.DTO;

import com.example.demo.model.BusinessProfile;
import com.example.demo.model.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileAPI_get_0 {
    private User user;
    private BusinessProfile businessProfile;
}

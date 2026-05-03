package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "expirydate")
    private LocalDateTime expiry_date;

    @Column(name = "used")
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "users_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public VerificationToken(String token, User user){
        this.token = token;
        this.user = user;
        this.expiry_date = LocalDateTime.now().plusMinutes(11);
    }
    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.expiry_date);
    }

}

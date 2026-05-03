package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification_medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verification_request_id", referencedColumnName = "id")
    private VerificationRequest verificationRequest;
}

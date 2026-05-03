package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "listing_medias")
public class listingsMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @JoinColumn(name = "listing_id")
    @ManyToOne
    public listings l;

    @JoinColumn(name = "media_id")
    @ManyToOne
    public media m;

}
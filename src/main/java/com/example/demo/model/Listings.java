package com.example.demo.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "listings")
public class listings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    public Users u;

    @Column(name = "title")
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "price")
    public float price;

    @Column(name = "capacity")
    public int capacity;

    @Column(name = "room_type",nullable = false)
    public Integer roomtype;

    @Column(name = "address")
    public String address;

    @Column(name = "status")
    public Integer status;

    @Column(name = "created_at",updatable = false,insertable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    public LocalDateTime CreateAt;
}


package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.websocket.Encoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.support.SqlBinaryValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "medias")
public class media {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "uniqueidentifier")
    public UUID id;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    public Users u;

    @Column(name = "media",columnDefinition = "VARBINARY(MAX)")
    public byte[] media;

    @Column(name = "file_type")
    public String fileType;

    @Column(name = "url")
    public String url;

    @Column(name = "date_created",insertable = false,updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    public LocalDateTime dateCreated;

    @Column(name = "status")
    public int status;

    @Column(name = "permission")
    public int permission;
}
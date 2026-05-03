package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(name = "media")
    @Basic(fetch = FetchType.LAZY)
    private byte[] media;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "url")
    private String URL;

    @Column(name = "date_created", insertable = false)
    private LocalDateTime dateCreated;

    @Column(name = "status")
    private int status;

    @Column(name = "permission")
    private int permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
}

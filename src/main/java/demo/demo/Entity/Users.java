package demo.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;


    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password_hash;

    @Column(name = "isenabled")
    private boolean enabled = false;

    @Column(name = "role")
    private Integer role;

    @OneToOne(mappedBy = "users",cascade = CascadeType.ALL)
    @JsonIgnore
    private Verification_token verificationToken;
}

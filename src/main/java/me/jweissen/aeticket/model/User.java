package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NonNull
    private String firstname;

    @Column(nullable = false)
    @NonNull
    private String lastname;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    @NonNull
    private Boolean admin;

    @OneToMany(mappedBy = "user")
    @JoinColumn(nullable = false)
    private List<Cart> carts;
}

package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;
}
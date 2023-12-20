package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

    @Column
    private Date tokenValidUntil;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private Role role;

    @OneToOne
    private Cart currentCart;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;
}
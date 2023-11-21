package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "cart")
    @JoinColumn(nullable = false)
    private List<CartEntry> cartEntries;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}

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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private Integer price;

    @Column(nullable = false)
    @NonNull
    private Integer stock;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Event event;

    @OneToMany(mappedBy = "category")
    private List<CartEntry> cartEntries;
}
package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean checkedOut = false;

    @OneToMany(mappedBy = "cart")
    @Column
    private List<CartEntry> cartEntries;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}

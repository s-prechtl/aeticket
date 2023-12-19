package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CartEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @NonNull
    @JoinColumn(nullable = false)
    private Cart cart;

    @ManyToOne
    @NonNull
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    @NonNull
    private Integer amount;
}

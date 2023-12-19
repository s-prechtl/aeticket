package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String description;

    @Column(nullable = false)
    @NonNull
    private Date start;

    @Column(nullable = false)
    @NonNull
    private Date end;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<Category> categories = new ArrayList<>();

}

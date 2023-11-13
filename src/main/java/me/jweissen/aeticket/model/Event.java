package me.jweissen.aeticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date start;

    @Column(nullable = false)
    private Date end;

    @OneToMany(mappedBy = "event")
    @Column(nullable = false)
    private List<Category> categories = new ArrayList<>();

}

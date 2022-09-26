package com.sudoku.oohub.domain;

import javax.persistence.*;

@Entity
public class Organization {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long id;
    private String name;
}

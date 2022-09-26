package com.sudoku.oohub.domain;

import javax.persistence.*;

@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;
    private String name;
}
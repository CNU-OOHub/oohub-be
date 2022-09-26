package com.sudoku.oohub.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Group {
    @Id
    private Long id;
    private String name;
}

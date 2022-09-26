package com.sudoku.oohub.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    private Long id;
    private String username;
    private String password;
    private Role role;

    private Department department;
    private Group group;

}

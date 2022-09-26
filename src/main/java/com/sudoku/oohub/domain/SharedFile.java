package com.sudoku.oohub.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SharedFile {
    @Id
    private Long id;
    private String filepath;
    private String filename;
    private String contents;
    private Long member_id;
    private Long group_id;

}

package com.sudoku.oohub.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class MemberOrganization {

    @Id
    @Column(name = "memberOrganization_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}

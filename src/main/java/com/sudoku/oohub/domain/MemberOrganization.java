package com.sudoku.oohub.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_organization_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}

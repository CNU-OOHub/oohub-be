package com.sudoku.oohub.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class MemberOrganization {

    @EmbeddedId
    private MemberOrganizationId id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("organizationId")
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}

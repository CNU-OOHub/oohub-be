package com.sudoku.oohub.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberOrganizationId implements Serializable {

    private String memberId;

    private String organizationId;
}

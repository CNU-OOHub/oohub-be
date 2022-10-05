package com.sudoku.oohub.repository;

import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    @Query(value = "select o.organization_id as id, o.name as name from member_organization mg left join organization o on mg.organization_id = o.organization_id where mg.member_id = :memberId",
            countQuery = "select count(*) from member_organization mg left join organization o on mg.organization_id = o.organization_id where mg.member_id = :memberId",
            nativeQuery = true
    )
    List<Object[]> findAllByMemberId(@Param("memberId") Long memberId);

}

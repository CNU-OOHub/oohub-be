package com.sudoku.oohub.repository;

import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    @Query(value = "select o.organization_id as id, o.name as name from member_organization mg left join organization o on mg.organization_id = o.organization_id where mg.member_id = :memberId",
            countQuery = "select count(*) from member_organization mg left join organization o on mg.organization_id = o.organization_id where mg.member_id = :memberId",
            nativeQuery = true
    )
    List<Object[]> findAllByMemberId(@Param("memberId") Long memberId);

    @Query(value = "select m.member_id as id, m.username as username, d.name as department\n" +
            "from member m inner join department d on m.department_id = d.department_id\n" +
            "right join member_organization mg on mg.member_id = m.member_id\n" +
            "where mg.organization_id = :organizationId",
            countQuery = "select count(*) from member_organization mg left join member m on mg.member_id = o.member_id where mg.organization_id = :organizationId",
            nativeQuery = true
    )
    List<Object[]> findAllByOrganizationId(@Param("organizationId") Long organizationId);

    Optional<MemberOrganization> findByMemberIdAndOrganizationId(@Param("member_id") Long member_id, @Param("organization_id") Long organization_id);

    void deleteByMemberIdAndOrganizationId(@Param("member_id") Long memberId, @Param("organization_id") Long organizationId);
}

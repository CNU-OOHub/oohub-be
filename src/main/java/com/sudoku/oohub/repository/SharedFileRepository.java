package com.sudoku.oohub.repository;

import com.sudoku.oohub.domain.SharedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {

    Optional<SharedFile> findByOrganizationId(@Param("organization_id") long organization_id);

    Optional<SharedFile> findByOrganizationIdAndFilename(@Param("organization_id") long organization_id, @Param("filename") String filename);

    void deleteByOrganizationIdAndFilename(@Param("organization_id") long organization_id, @Param("filename") String filename);

    Optional<SharedFile> findByFilepath(@Param("filepath") String filePath);
}

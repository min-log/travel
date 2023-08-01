package com.example.travel.repository.travel;

import com.example.travel.domain.FileContent;
import com.example.travel.domain.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileContentRepository extends JpaRepository<FileContent,Long> {

    Optional<FileContent> getFileContentByOriginFileName(String originFileName);

}

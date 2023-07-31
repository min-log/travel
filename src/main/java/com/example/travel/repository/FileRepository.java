package com.example.travel.repository;

import com.example.travel.domain.FileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileContent,Long> {

}

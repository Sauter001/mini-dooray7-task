package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

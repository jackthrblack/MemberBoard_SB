package com.mb.kbj.memberboard.repository;

import com.mb.kbj.memberboard.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}

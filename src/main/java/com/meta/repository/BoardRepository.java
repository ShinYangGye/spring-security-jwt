package com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}

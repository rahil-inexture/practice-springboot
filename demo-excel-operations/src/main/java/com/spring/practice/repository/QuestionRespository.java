package com.spring.practice.repository;

import com.spring.practice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRespository extends JpaRepository<Question, Long> {
}

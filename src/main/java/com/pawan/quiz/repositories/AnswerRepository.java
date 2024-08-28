package com.pawan.quiz.repositories;

import com.pawan.quiz.model.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Long countByResultId(Long resultId);

    List<Answer> findByResultId(Long resultId);

}

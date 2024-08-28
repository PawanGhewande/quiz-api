package com.pawan.quiz.repositories;

import com.pawan.quiz.model.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<List<Result>> findByUserName(String userName);

    Optional<List<Result>> findByUserNameAndId(String userId, Long quizId);

    Optional<List<Result>> findByUserNameAndQuizId(String userId, Long quizId);
}

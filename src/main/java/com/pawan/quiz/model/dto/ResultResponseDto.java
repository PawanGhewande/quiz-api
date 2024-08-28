package com.pawan.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDto {
    private QuizResultResponseDto quiz;
    private String userName;
    private int score;
    private List<AnswerResponseDto> answers;
}

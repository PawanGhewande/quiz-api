package com.pawan.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
@NonNull
    private Long quizId;
@NonNull
    private String userName;
@NonNull
    private List<AnswerDto> answers;
}

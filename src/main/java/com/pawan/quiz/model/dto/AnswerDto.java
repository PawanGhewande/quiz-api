package com.pawan.quiz.model.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
@NonNull
    private Integer questionId;
@Nullable
    private Integer selectedOptionId;
}

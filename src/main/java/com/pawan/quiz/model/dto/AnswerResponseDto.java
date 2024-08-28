package com.pawan.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDto {
    private QuestionResponseDto question;
    private OptionsResponseDto selectedOption;
}

package com.pawan.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private long id;
    private String text;
    @JsonIgnoreProperties(value = {"isCorrect"})
    private List<OptionsResponseDto> options;
}

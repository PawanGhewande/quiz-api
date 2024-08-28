package com.pawan.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  class QuestionDto {
        @NonNull
        private String text;
        @NonNull
        private List<OptionsDto> options;
}

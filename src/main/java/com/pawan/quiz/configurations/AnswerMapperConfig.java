package com.pawan.quiz.configurations;

import com.pawan.quiz.model.dto.OptionsDto;
import com.pawan.quiz.model.entities.Option;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnswerMapperConfig {

    @Bean
    public PropertyMap<OptionsDto, Option> answerMapper() {
        return new PropertyMap<OptionsDto, Option>() {
            @Override
            protected void configure() {
                map().setIsCorrect(source.isCorrect());
            }
        };
    }
}

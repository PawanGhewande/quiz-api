package com.pawan.quiz.controller;

import com.pawan.quiz.controllers.QuizController;
import com.pawan.quiz.model.dto.QuizDto;
import com.pawan.quiz.model.dto.QuizResponseDto;
import com.pawan.quiz.model.entities.Quiz;
import com.pawan.quiz.services.QuizService;
import com.pawan.quiz.services.ResultService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class QuizControllerTest {

    @Autowired
    private QuizController quizController;

    @MockBean
    private QuizService quizService;

    @MockBean
    private ResultService resultService;

    @Test
    public void testCreateValid() throws Exception {

        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("Test Quiz");

        Quiz savedQuiz = new Quiz();
        savedQuiz.setId(1L);
        savedQuiz.setTitle("Test Quiz");
        Mockito.when(quizService.createQuiz(quizDto)).thenReturn(savedQuiz);

        ResponseEntity<Object> response = quizController.createQuiz(quizDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assert responseBody != null;
        assertEquals("Quiz is created ID: 1, To check response use /quizzes/id", responseBody.get("message"));
    }


    @Test
    public void testCreateQuizException() {
        QuizDto invalidDto = new QuizDto();
        assertThrows(NullPointerException.class, () -> quizController.createQuiz(invalidDto));
    }
}

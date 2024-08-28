package com.pawan.quiz.services;

import com.pawan.quiz.model.dto.OptionsDto;
import com.pawan.quiz.model.dto.QuestionDto;
import com.pawan.quiz.model.dto.QuizDto;
import com.pawan.quiz.model.dto.QuizResponseDto;
import com.pawan.quiz.model.entities.Option;
import com.pawan.quiz.model.entities.Question;
import com.pawan.quiz.model.entities.Quiz;
import com.pawan.quiz.repositories.OptionRepository;
import com.pawan.quiz.repositories.QuestionRepository;
import com.pawan.quiz.repositories.QuizRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @MockBean
    private QuizRepository quizRepository;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private OptionRepository optionRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testCreateQuizValid() {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("Test Quiz");

        Quiz mockQuiz = new Quiz();
        mockQuiz.setTitle("Test Quiz");

        OptionsDto optionsDto=new OptionsDto();
        optionsDto.setText("Option");
        optionsDto.setIsCorrect(true);

        QuestionDto questionDto=new QuestionDto();
        questionDto.setText("Question 1");
        questionDto.setOptions(List.of(optionsDto));
        List<QuestionDto> questionDtos = List.of(questionDto);
        quizDto.setQuestions(questionDtos);

        Option option=new Option();
        option.setId(1L);
        option.setText("Option");
        option.setIsCorrect(true);

        Question question=new Question();
        question.setId(1L);
        question.setText("Question 1");
        question.setOptions(List.of(option));
        List<Question> mockQuestions = List.of(question);
        mockQuiz.setQuestions(mockQuestions);

        // Mock behavior
        Mockito.when(modelMapper.map(quizDto, Quiz.class)).thenReturn(mockQuiz);
        Mockito.when(quizRepository.saveAndFlush(mockQuiz)).thenReturn(mockQuiz);
        Mockito.when(modelMapper.map(questionDtos.getFirst(), Question.class)).thenReturn(mockQuestions.getFirst());
        Mockito.when(modelMapper.map(optionsDto, Option.class)).thenReturn(option);

        Mockito.when(questionRepository.save(mockQuestions.getFirst())).thenReturn(mockQuestions.getFirst());

        // Call method and assert
        Quiz createdQuiz = quizService.createQuiz(quizDto);
        assertNotNull(createdQuiz);
        assertEquals(mockQuiz.getId(), createdQuiz.getId());
        assertEquals(mockQuiz.getTitle(), createdQuiz.getTitle());
    }

    @Test
    public void testCreateQuizInvalidInputsThrowsException() {
        QuizDto invalidDto = new QuizDto();

        assertThrows(NullPointerException.class, () -> quizService.createQuiz(invalidDto));
    }

    @Test
    public void testGetQuizById_existingId_returnsQuiz() {
        Long id = 1L;
        Quiz mockQuiz = new Quiz();
        mockQuiz.setId(id);
        mockQuiz.setTitle("Test Quiz");

        QuizResponseDto mockResponseDto = new QuizResponseDto();
        mockResponseDto.setId(id);
        mockResponseDto.setTitle("Test Quiz");


        Mockito.when(quizRepository.findById(id)).thenReturn(Optional.of(mockQuiz));
        Mockito.when(modelMapper.map(mockQuiz, QuizResponseDto.class)).thenReturn(mockResponseDto);

        QuizResponseDto response = quizService.getQuizById(id);
        assertNotNull(response);
        assertEquals(mockResponseDto.getId(), response.getId());
        assertEquals(mockResponseDto.getTitle(), response.getTitle());
    }

    @Test
    public void testCreateQuizThrowsException() {
        QuizDto invalidDto = new QuizDto();
        invalidDto.setTitle("Exception");
        invalidDto.setQuestions(List.of(new QuestionDto()));
        assertThrows(NullPointerException.class, () -> quizService.createQuiz(invalidDto));
    }
}
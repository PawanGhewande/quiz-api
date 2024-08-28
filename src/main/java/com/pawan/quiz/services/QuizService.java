package com.pawan.quiz.services;

import com.pawan.quiz.model.dto.QuestionDto;
import com.pawan.quiz.model.dto.QuizDto;
import com.pawan.quiz.model.dto.QuizResponseDto;
import com.pawan.quiz.model.entities.Option;
import com.pawan.quiz.model.entities.Question;
import com.pawan.quiz.model.entities.Quiz;
import com.pawan.quiz.repositories.OptionRepository;
import com.pawan.quiz.repositories.QuestionRepository;
import com.pawan.quiz.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quiz.setQuestions(null);
        Quiz savedQuiz = quizRepository.saveAndFlush(quiz);

        for (QuestionDto questionDto : quizDto.getQuestions()) {
            Question question = modelMapper.map(questionDto, Question.class);
            question.setQuiz(savedQuiz);
            question.setOptions(null);
            Question savedQuestion = questionRepository.save(question);

            List<Option> answers = questionDto.getOptions().stream()
                    .map(answerDto -> {
                       Option option=modelMapper.map(answerDto, Option.class);
                       option.setIsCorrect(answerDto.isCorrect());
                       return option;
                    })

                    .peek(option -> option.setQuestion(savedQuestion))
                    .collect(Collectors.toList());
            optionRepository.saveAll(answers);
        }
        return savedQuiz;
    }

    public QuizResponseDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return modelMapper.map(quiz, QuizResponseDto.class);
    }
}


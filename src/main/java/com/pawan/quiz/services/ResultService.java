package com.pawan.quiz.services;

import com.pawan.quiz.model.dto.ResultDto;
import com.pawan.quiz.model.dto.ResultResponseDto;
import com.pawan.quiz.model.entities.Answer;
import com.pawan.quiz.model.entities.Result;
import com.pawan.quiz.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Result createResult(ResultDto result) {
        Result result1=new Result();
        result1.setQuiz(quizRepository.findById(result.getQuizId()).orElseThrow());
        result1.setUserName(result.getUserName());
        result1=resultRepository.save(result1);
        Result finalResult = result1;
        result.getAnswers().stream().map(answerDto->modelMapper.map(answerDto, Answer.class))
                .peek(answer -> {
                    answer.setResult(finalResult);
                    answerRepository.save(answer);
                }).collect(Collectors.toList());;
        return result1;
    }

    public ResultResponseDto getResultById(Long id) {
        Optional<Result> byId = resultRepository.findById(id);
        Result result1= new Result();
        if(byId.isPresent()){
            Result result=byId.get();
            List<Answer> byResultId = answerRepository.findByResultId(id);
            System.out.println(byResultId.toArray());
            result.setAnswers(byResultId);
            List<Answer> attempt = answerRepository.findByResultId(result.getId());
            long correct = attempt.stream().filter(answer -> answer.getSelectedOption().getIsCorrect()).count();
            result.setScore(calculateScore(attempt.size(),correct));
            result1=result;
        }
       return modelMapper.map(result1, ResultResponseDto.class);
    }

    public List<ResultResponseDto> getResultByUserName(String userName) {
        Optional<List<Result>> byUserName = resultRepository.findByUserName(userName);
        List<ResultResponseDto> data=new ArrayList<>();
        if(byUserName.isPresent()){
           for(Result result:byUserName.get()) {
               List<Answer> byResultId = answerRepository.findByResultId(result.getId());
               result.setAnswers(byResultId);
               List<Answer> attempt = answerRepository.findByResultId(result.getId());
               long correct = attempt.stream().filter(answer -> answer.getSelectedOption().getIsCorrect()).count();
               result.setScore(calculateScore( attempt.size(), correct));
               data.add(modelMapper.map(result, ResultResponseDto.class));
           }
        }
        return data;
    }

    public List<ResultResponseDto> getResultByUserNameAndResultId(String userName, long id) {
        Optional<List<Result>> byUserName = resultRepository.findByUserNameAndId(userName,id);
        List<ResultResponseDto> data=new ArrayList<>();
        if(byUserName.isPresent()){
            for(Result result:byUserName.get()) {
                List<Answer> byResultId = answerRepository.findByResultId(result.getId());
                result.setAnswers(byResultId);
                List<Answer> attempt = answerRepository.findByResultId(result.getId());
                long correct = attempt.stream().filter(answer -> answer.getSelectedOption().getIsCorrect()).count();
                result.setScore(calculateScore( attempt.size(), correct));
                data.add(modelMapper.map(result, ResultResponseDto.class));
            }
        }
        return data;
    }

    public List<ResultResponseDto> getResultByUserNameAndQuizId(String userName, long id) {
        Optional<List<Result>> byUserName = resultRepository.findByUserNameAndQuizId(userName,id);
        List<ResultResponseDto> data=new ArrayList<>();
        if(byUserName.isPresent()){
            for(Result result:byUserName.get()) {
                List<Answer> byResultId = answerRepository.findByResultId(result.getId());
                result.setAnswers(byResultId);
                List<Answer> attempt = answerRepository.findByResultId(result.getId());
                long correct = attempt.stream().filter(answer -> answer.getSelectedOption().getIsCorrect()).count();
                result.setScore(calculateScore(attempt.size(), correct));
                data.add(modelMapper.map(result, ResultResponseDto.class));
            }
        }
        return data;
    }

    public int calculateScore(long attendedQuestions, long correctAnswers) {
        if (attendedQuestions == 0) {
            return 0;
        }
        return (int) (((double) correctAnswers / attendedQuestions) * 100);
    }

}

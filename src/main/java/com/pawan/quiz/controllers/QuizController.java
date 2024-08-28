package com.pawan.quiz.controllers;

import com.pawan.quiz.model.dto.QuizDto;
import com.pawan.quiz.model.dto.QuizResponseDto;
import com.pawan.quiz.model.dto.ResultDto;
import com.pawan.quiz.model.dto.ResultResponseDto;
import com.pawan.quiz.model.entities.Quiz;
import com.pawan.quiz.model.entities.Result;
import com.pawan.quiz.services.QuizService;
import com.pawan.quiz.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<Object> createQuiz(@Valid @RequestBody QuizDto saveQuiz) {
        Quiz quiz=quizService.createQuiz(saveQuiz);
        Map<String,String> map=new HashMap<>();
        map.put("message","Quiz is created ID: "+quiz.getId()+", To check response use /quizzes/id");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuizResponseDto getQuiz(@PathVariable Long id) {
        return  quizService.getQuizById(id);
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<Object> submitAnswer(@Valid @RequestBody ResultDto resultDto) {
         Result result= resultService.createResult(resultDto);
         Map<String,String> map=new HashMap<>();
         map.put("message","Submitted your response "+result.getId()+", To check response use /result/id");
         return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/results/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultResponseDto getResult(@PathVariable Long id) {
        return resultService.getResultById(id);
    }


    @GetMapping("/results/{user}/byQuizId")
    @ResponseStatus(HttpStatus.OK)
    public List<ResultResponseDto> getResultByQuizId(@PathVariable String user, @RequestParam Long id) {
        return resultService.getResultByUserNameAndQuizId(user, id);
    }

    @GetMapping("/results/{user}/byResultId")
    @ResponseStatus(HttpStatus.OK)
    public List<ResultResponseDto> getResultByResultId(@PathVariable String user, @RequestParam Long id) {
        return resultService.getResultByUserNameAndResultId(user, id);
    }

    @GetMapping("/results/byUser/{user}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResultResponseDto> getResultByUser(@PathVariable String user) {
        return resultService.getResultByUserName(user);
    }
}


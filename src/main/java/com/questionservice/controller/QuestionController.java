package com.questionservice.controller;


import com.questionservice.model.QuestionModel;
import com.questionservice.model.QuestionWrapper;
import com.questionservice.model.Response;
import com.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<QuestionModel>> getAllQuestions(){
        return questionService.getQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<QuestionModel>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionModel questionModel){
        return questionService.addQuestion(questionModel);
    }

    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }

//    Things needed to be done

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String category , @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(category , numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestParam List<Integer> questionsId){
        return questionService.getQuestionsFromId(questionsId);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}

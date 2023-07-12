package com.questionservice.service;


import com.questionservice.model.QuestionModel;
import com.questionservice.model.QuestionWrapper;
import com.questionservice.model.Response;
import com.questionservice.repositry.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
        @Autowired
        QuestionDao questionDao;
       public ResponseEntity<List<QuestionModel>> getQuestions(){
           try {
               return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
           }catch(Exception e){
               e.printStackTrace();
               return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
           }
       }

       public ResponseEntity<List<QuestionModel>> getQuestionsByCategory(String category){
            try{
                return new ResponseEntity<>(questionDao.findByCategory(category) , HttpStatus.OK);
            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new ArrayList<>() , HttpStatus.BAD_REQUEST);
            }
       }


    public ResponseEntity<String> addQuestion(QuestionModel questionModel) {
            try{
                questionDao.save(questionModel);
                return new ResponseEntity<>("Success", HttpStatus.CREATED);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
            }
    }

    public String deleteQuestion(Integer id) {
           questionDao.deleteById(id);
           return "deleted successfully";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category , Integer num) {
           try {
               List<Integer> questions = questionDao.findRandomQuestionByCategory(num, category);
               return new ResponseEntity<>(questions , HttpStatus.OK);
           }catch (Exception e){
               e.printStackTrace();
               return new ResponseEntity<>(new ArrayList<>() , HttpStatus.BAD_REQUEST);
           }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsId) {
           List<QuestionWrapper> wrappers = new ArrayList<>();
           List<QuestionModel> questions = new ArrayList<>();
           for(Integer id : questionsId){
               if(questionDao.findById(id).isPresent()) {
                   questions.add(questionDao.findById(id).get());
               }
           }

           for(QuestionModel e : questions){
               QuestionWrapper wrapper = new QuestionWrapper();
               wrapper.setId(e.getId());
               wrapper.setQuestionTitle(e.getQuestionTitle());
               wrapper.setOption1(e.getOption1());
               wrapper.setOption2(e.getOption2());
               wrapper.setOption3(e.getOption3());
               wrapper.setOption4(e.getOption4());
               wrappers.add(wrapper);
           }
           return new ResponseEntity<>(wrappers , HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
           int right = 0;
           for(Response response : responses){
               if(questionDao.findById(response.getId()).isPresent()) {
                   QuestionModel questionModel = questionDao.findById(response.getId()).get();
                   if(response.getResponse().equals(questionModel.getRightAnswer())){
                       right++;
                   }
               }
           }
           return new ResponseEntity<>(right , HttpStatus.OK);
    }
}

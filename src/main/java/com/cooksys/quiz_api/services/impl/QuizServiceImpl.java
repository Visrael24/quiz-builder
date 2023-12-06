package com.cooksys.quiz_api.services.impl;


import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionMapper questionMapper;

//  Success, returns a collection of quiz elements
  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    List<Quiz> quizzes = quizRepository.findAll();
    return quizMapper.entitiesToDtos(quizzes);
  }

//  Success! Adds everything properly to the db and displays it when we getAllQuizzes
  @Override
  public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
    Quiz newQuiz = quizMapper.requestToEntity(quizRequestDto);
    List<Question> questions = questionMapper.requestToEntities(quizRequestDto.getQuestions());
    for (Question question : questions) {
      question.setQuiz(newQuiz);
      List<Answer> answers = question.getAnswers();
      for (Answer answer : answers) {
        answer.setQuestion(question);
      }
    }
    newQuiz.setQuestions(questions);
    quizRepository.save(newQuiz);
    return quizMapper.entityToDto(newQuiz);
  }

// Success! deletes the quiz by the given ID and returns the deleted quiz
  @Override
  public QuizResponseDto deleteQuiz(Long id) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz == null) {
      throw new IllegalArgumentException("Not working");
    }
    quizRepository.delete(quiz);
    return quizMapper.entityToDto(quiz);
  }


// Success! Renames the quiz by id to the name given.
  @Override
  public QuizResponseDto renameQuiz(Long id, String newName) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz != null) {
      quiz.setName(newName);
      quizRepository.save(quiz);
    }
    return quizMapper.entityToDto(quiz);
  }

//  Success! grabs a random question from the requested quiz
  @Override
  public QuestionResponseDto getRandomQuestion(Long id) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz != null && !quiz.getQuestions().isEmpty()) {
      List<Question> questions = quiz.getQuestions();
      int randomIndex = (int) (Math.random() * questions.size());
      Question randomQuestion = questions.get(randomIndex);
      return questionMapper.entityToDto(randomQuestion);
    }
    return null;
  }

// Success! Adds a question to the quiz by id and properly updates the database
  @Override
  public QuizResponseDto addQuestionToQuiz(Long id, QuestionRequestDto addQuestionRequest) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz != null) {
      Question newQuestion = questionMapper.requestToEntity(addQuestionRequest);
      newQuestion.setQuiz(quiz);
      List<Answer> answers = newQuestion.getAnswers();
      if (answers != null) {
        for (Answer answer : answers) {
          answer.setQuestion(newQuestion);
        }
      }
      newQuestion.setQuiz(quiz);
      quiz.getQuestions().add(newQuestion);
      quizRepository.save(quiz);
    }
    return quizMapper.entityToDto(quizRepository.getById(id));
  }

// SUCCESS!!!! Deletes the question by id from the quiz by id and returns the deleted question.
  @Override
  public QuestionResponseDto deleteQuestionFromQuiz(Long id, Long questionID) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz != null) {
      List<Question> questions = quiz.getQuestions();
      Question questionToDelete = questions.stream()
              .filter(q -> q.getId().equals(questionID))
              .findFirst()
              .orElse(null);
      if (questionToDelete != null) {
        questions.remove(questionToDelete);
        quizRepository.save(quiz);
          return questionMapper.entityToDto(questionToDelete);
      }
    }
    return null;
  }
}

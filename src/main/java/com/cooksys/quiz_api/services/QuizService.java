package com.cooksys.quiz_api.services;


import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;

import java.util.List;

public interface QuizService {
  List<QuizResponseDto> getAllQuizzes();
  QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);
  QuizResponseDto deleteQuiz(Long id);
  QuizResponseDto renameQuiz(Long id, String newName);
  QuestionResponseDto getRandomQuestion(Long id);
  QuizResponseDto addQuestionToQuiz(Long id, QuestionRequestDto answers);
  QuestionResponseDto deleteQuestionFromQuiz(Long id, Long questionID);
}

package com.cooksys.quiz_api.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class AnswerResponseDto {

  public Long id;

  public String text;

//  private boolean correct;
}

package com.cooksys.quiz_api.dtos;
import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Data
@Getter
@Setter
public class QuestionRequestDto {
    public String text;
    public List<AnswerRequestDto> answers;

}

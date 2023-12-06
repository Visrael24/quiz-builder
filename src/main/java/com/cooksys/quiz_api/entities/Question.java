package com.cooksys.quiz_api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@NoArgsConstructor
@Data
public class Question {

  @Id
  @GeneratedValue
  public Long id;

  public String text;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  public Quiz quiz;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Answer> answers;

  public Question() {
    answers = new ArrayList<>();
  }

}

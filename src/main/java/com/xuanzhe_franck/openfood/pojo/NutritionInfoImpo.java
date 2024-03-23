package com.xuanzhe_franck.openfood.pojo;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
public class NutritionInfoImpo {
  static int id;
  String barCode;
  String name;
  int nutritionScore;
  String classe;
  String color;
  public NutritionInfoImpo(){
    id++;
  }
}

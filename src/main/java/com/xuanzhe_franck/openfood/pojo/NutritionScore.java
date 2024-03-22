package com.xuanzhe_franck.openfood.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionScore {
  @Id
  private int ID;
  private String CLASSE;
  private double UPPER_BOUND;
  private double LOWER_BOUND;
  private String COLOR;
}

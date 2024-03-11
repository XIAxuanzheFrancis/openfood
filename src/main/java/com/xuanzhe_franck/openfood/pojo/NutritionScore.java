package com.xuanzhe_franck.openfood.pojo;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionScore {
  private int ID;
  private String CLASS;
  private double UPPER_BOUND;
  private double LOWER_BOUND;
  private String COLOR;
}

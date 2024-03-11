package com.xuanzhe_franck.openfood.pojo;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
  private int ID;
  private String NAME;
  private int POINTS;
  private double MIN_BOUND;
  private String COMPONENT;
}

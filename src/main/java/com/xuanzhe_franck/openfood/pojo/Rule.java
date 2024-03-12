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
public class Rule {
  @Id
  private int ID;
  private String NAME;
  private int POINTS;
  private double MIN_BOUND;
  private String COMPONENT;
}

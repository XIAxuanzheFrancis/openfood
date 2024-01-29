package com.xuanzhe_franck.openfood.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private int code;
  private String product_name;
  private String nutrition_grades;
  private double fiber_100g;
  private double proteins_100g;
  private double energy_100g;
  private double saturated_fat_100g;
  private double sugar_100g;
  private double salt_100g;
}

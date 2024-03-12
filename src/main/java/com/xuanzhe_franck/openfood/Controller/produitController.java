package com.xuanzhe_franck.openfood.controller;

import com.xuanzhe_franck.openfood.service.NutritionScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class produitController {
  @Autowired
  private NutritionScoreService nutritionScoreService;

  @GetMapping("/product/{barcode}")
  public String getProductByBarcode(@PathVariable String barcode) {
    // Calculate the nutrition score and retrieve other product information
    String productName = "Biscuits fourrés parfum chocolat (35%) - Prince Goût Chocolat au Blé Complet";
    String nutritionScore = nutritionScoreService.calculateNutritionScore(barcode);
    return nutritionScore;
  }
  @GetMapping("/hello")
  public String test1() {
    return "hello";
  }
}

package com.xuanzhe_franck.openfood.controller;

import com.xuanzhe_franck.openfood.pojo.NutritionInfoImpo;
import com.xuanzhe_franck.openfood.pojo.NutritionInformation;
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
  public NutritionInfoImpo getProductByBarcode(@PathVariable String barcode) {
    // Calculate the nutrition score and retrieve other product information
    NutritionInformation nutritionInformation = new NutritionInformation();
    nutritionInformation = nutritionScoreService.calculateNutritionScore(barcode);
    NutritionInfoImpo nutritionInfoImpo = new NutritionInfoImpo();
    nutritionInfoImpo.setBarCode(barcode);
    nutritionInfoImpo.setName(nutritionInformation.getProduct_name());
    nutritionInfoImpo.setClasse(nutritionInformation.getNutrition_grades());
    nutritionInfoImpo.setNutritionScore(nutritionInformation.getNutritionScore());
    nutritionInfoImpo.setColor(nutritionInformation.getColor());
    return nutritionInfoImpo;
  }
  @GetMapping("/hello")
  public String test1() {
    return "hello";
  }
}

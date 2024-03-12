package com.xuanzhe_franck.openfood.service;

import com.xuanzhe_franck.openfood.Repository.NutritionScoreRepository;
import com.xuanzhe_franck.openfood.Repository.RuleRepository;
import com.xuanzhe_franck.openfood.pojo.NutritionInformation;
import com.xuanzhe_franck.openfood.pojo.NutritionScore;
import com.xuanzhe_franck.openfood.pojo.Rule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NutritionScoreService {

  @Autowired
  private NutritionScoreRepository nutritionScoreRepository;

  @Autowired
  private RuleRepository ruleRepository;

  public String calculateNutritionScore(String barcode) {
    NutritionInformation nutritionInformation = retrieveNutritionInformationFromAPI(barcode);
    double negativeComponent = calculateNegativeComponent(nutritionInformation);
    double positiveComponent = calculatePositiveComponent(nutritionInformation);
    int nutritionScore = calculateOverallScore(negativeComponent, positiveComponent);
    return saveNutritionScoreToDatabase(barcode, nutritionScore);
  }

  public NutritionInformation retrieveNutritionInformationFromAPI(String barcode) {
    String apiUrl = "https://fr.openfoodfacts.org/api/v0/produit/" + barcode + ".json";
    RestTemplate restTemplate = new RestTemplate();
    NutritionInformation response = restTemplate.getForObject(apiUrl, NutritionInformation.class);
    if (response != null && response.getCode() == 1) {
      return response;
    } else {
      throw new RuntimeException("Failed to retrieve nutrition information from Open Food Facts API");
    }
  }

  private double calculateNegativeComponent(NutritionInformation nutritionInformation) {
    List<Rule> negativeRules = ruleRepository.findAll();
    double negativeComponent = 0.0;
    for (Rule rule : negativeRules) {
      if ("energy_100g".equals(rule.getNAME()) && nutritionInformation.getEnergy_100g() > rule.getMIN_BOUND()) {
        negativeComponent += rule.getPOINTS();
      } else if ("saturated_fat_100g".equals(rule.getNAME()) && nutritionInformation.getSaturated_fat_100g() > rule.getMIN_BOUND()) {
        negativeComponent += rule.getPOINTS();
      } else if ("sugars_100g".equals(rule.getNAME()) && nutritionInformation.getSugar_100g() > rule.getMIN_BOUND()) {
        negativeComponent += rule.getPOINTS();
      } else if ("salt_100g".equals(rule.getNAME()) && nutritionInformation.getSalt_100g() > rule.getMIN_BOUND()) {
        negativeComponent += rule.getPOINTS();
      }
    }

    return negativeComponent;
  }


  private double calculatePositiveComponent(NutritionInformation nutritionInformation) {
    List<Rule> positiveRules = ruleRepository.findAll();
    double positiveComponent = 0.0;

    for (Rule rule : positiveRules) {
      if ("fiber_100g".equals(rule.getNAME()) && nutritionInformation.getFiber_100g() > rule.getMIN_BOUND()) {
        positiveComponent += rule.getPOINTS();
      } else if ("proteins_100g".equals(rule.getNAME()) && nutritionInformation.getProteins_100g() > rule.getMIN_BOUND()) {
        positiveComponent += rule.getPOINTS();
      }
    }

    return positiveComponent;
  }


  private int calculateOverallScore(double negativeComponent, double positiveComponent) {
    int nutritionScore = (int) Math.round(positiveComponent - negativeComponent);
    return nutritionScore;
  }

  private String saveNutritionScoreToDatabase(String barcode, int nutritionScore) {

    // Retrieve all score ranges from the database
    List<NutritionScore> scoreRanges = nutritionScoreRepository.findAll();

    // Find the appropriate evaluation based on the calculated nutrition score
    String evaluation = "Unknown";

    for (NutritionScore range : scoreRanges) {
      if (nutritionScore >= range.getLOWER_BOUND() && nutritionScore <= range.getUPPER_BOUND()) {
        evaluation = range.getCLASS();
        break;
      }
    }

    return evaluation;
  }
}



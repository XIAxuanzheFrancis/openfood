package com.xuanzhe_franck.openfood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanzhe_franck.openfood.Repository.NutritionScoreRepository;
import com.xuanzhe_franck.openfood.Repository.RuleRepository;
import com.xuanzhe_franck.openfood.pojo.NutritionInformation;
import com.xuanzhe_franck.openfood.pojo.NutritionScore;
import com.xuanzhe_franck.openfood.pojo.Rule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    String nutritionGrade = evaluateNutritionScore(nutritionScore);
    nutritionInformation.setNutrition_grades(nutritionGrade);

    return nutritionGrade;
  }

  public NutritionInformation retrieveNutritionInformationFromAPI(String barcode) {
    String apiUrl = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      String responseBody = responseEntity.getBody();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        // Extract necessary information from JSON
        String productName = rootNode.path("product").path("product_name").asText();
        String productBarcode = rootNode.path("product").path("code").asText();
        String productNutriscore = rootNode.path("product").path("nutrition_grades").asText();
        double fiber_100g = rootNode.path("product").path("nutriments").path("fiber_100g").asDouble();
        double proteins_100g = rootNode.path("product").path("nutriments").path("proteins_100g").asDouble();
        double energy_100g = rootNode.path("product").path("nutriments").path("energy_100g").asDouble();
        double saturated_fat_100g = rootNode.path("product").path("nutriments").path("saturated-fat_100g").asDouble();
        double sugars_100g = rootNode.path("product").path("nutriments").path("sugars_100g").asDouble();
        double salt_100g = rootNode.path("product").path("nutriments").path("salt_100g").asDouble();
//        private String code;
//        private String product_name;
//        private String nutrition_grades;
//        private double fiber_100g;
//        private double proteins_100g;
//        private double energy_100g;
//        private double saturated_fat_100g;
//        private double sugar_100g;
//        private double salt_100g;
        NutritionInformation nutritionInformation = new NutritionInformation();
        nutritionInformation.setProduct_name(productName);
        nutritionInformation.setCode(productBarcode);
        nutritionInformation.setFiber_100g(fiber_100g);
        nutritionInformation.setEnergy_100g(energy_100g);
        nutritionInformation.setProteins_100g(proteins_100g);
        nutritionInformation.setSaturated_fat_100g(saturated_fat_100g);
        nutritionInformation.setSugars_100g(sugars_100g);
        nutritionInformation.setSalt_100g(salt_100g);

        // Set other attributes similarly
        return nutritionInformation;
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to process JSON response from Open Food Facts API");
      }
    } else {
      throw new RuntimeException("Failed to retrieve nutrition information from Open Food Facts API");
    }
  }

  private double calculateNegativeComponent(NutritionInformation nutritionInformation) {
    List<Rule> negativeRules = ruleRepository.findAll();
    double negativeComponent = 0.0;
    for (Rule rule : negativeRules) {
      if ("energy_100g".equals(rule.getNAME()) && nutritionInformation.getEnergy_100g() < rule.getMIN_BOUND()) {
        negativeComponent += (rule.getPOINTS()-1);
        break;
      }
    }
    for (Rule rule : negativeRules) {
      if ("saturated_fat_100g".equals(rule.getNAME()) && nutritionInformation.getSaturated_fat_100g() < rule.getMIN_BOUND()) {
        negativeComponent += (rule.getPOINTS()-1);
        break;
      }
    }

    for (Rule rule : negativeRules) {
      if ("sugars_100g".equals(rule.getNAME()) && nutritionInformation.getSugars_100g() < rule.getMIN_BOUND()) {
        negativeComponent += (rule.getPOINTS()-1);
        break;
      }
    }

    for (Rule rule : negativeRules) {
      if ("salt_100g".equals(rule.getNAME()) && nutritionInformation.getSalt_100g() < rule.getMIN_BOUND()) {
        negativeComponent += (rule.getPOINTS()-1);
        break;
      }
    }
    return negativeComponent;
  }


  private double calculatePositiveComponent(NutritionInformation nutritionInformation) {
    List<Rule> positiveRules = ruleRepository.findAll();
    double positiveComponent = 0.0;

    for (Rule rule : positiveRules) {
      if ("fiber_100g".equals(rule.getNAME()) && nutritionInformation.getFiber_100g() < rule.getMIN_BOUND()) {
        positiveComponent += (rule.getPOINTS()-1);
        break;
      }
    }
    for (Rule rule : positiveRules) {
      if ("proteins_100g".equals(rule.getNAME()) && nutritionInformation.getProteins_100g() < rule.getMIN_BOUND()) {
        positiveComponent += (rule.getPOINTS()-1);
        break;
      }
    }
    return positiveComponent;
  }


  private int calculateOverallScore(double negativeComponent, double positiveComponent) {
    int nutritionScore = (int) Math.round(negativeComponent - positiveComponent);
    return nutritionScore;
  }

  private String evaluateNutritionScore(int nutritionScore) {

    // Retrieve all score ranges from the database
    List<NutritionScore> scoreRanges = nutritionScoreRepository.findAll();

    // Find the appropriate evaluation based on the calculated nutrition score
    String evaluation = "Unknown";

    for (NutritionScore range : scoreRanges) {
      if (nutritionScore >= range.getLOWER_BOUND() && nutritionScore <= range.getUPPER_BOUND()) {
        evaluation = range.getCLASSE();
        break;
      }
    }

    return evaluation;
  }
}



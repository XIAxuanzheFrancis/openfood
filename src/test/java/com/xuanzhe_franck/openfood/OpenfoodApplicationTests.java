package com.xuanzhe_franck.openfood;

import com.xuanzhe_franck.openfood.pojo.NutritionInfoImpo;
import com.xuanzhe_franck.openfood.pojo.NutritionInformation;
import com.xuanzhe_franck.openfood.pojo.Panir;
import com.xuanzhe_franck.openfood.service.NutritionScoreService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenfoodApplicationTests {
  @Autowired
  NutritionScoreService nutritionScoreService;
  private static Panir panir=new Panir();
  @Test
  public void test1()
  {
    NutritionInformation nutritionInformation = new NutritionInformation();
    nutritionInformation = nutritionScoreService.calculateNutritionScore("7622210449283");
    NutritionInfoImpo nutritionInfoImpo = new NutritionInfoImpo();
    nutritionInfoImpo.setBarCode("7622210449283");
    nutritionInfoImpo.setName(nutritionInformation.getProduct_name());
    nutritionInfoImpo.setClasse(nutritionInformation.getNutrition_grades());
    nutritionInfoImpo.setNutritionScore(nutritionInformation.getNutritionScore());
    nutritionInfoImpo.setColor(nutritionInformation.getColor());
    Assert.assertEquals(nutritionInfoImpo.getName(),"Prince Chocolat biscuits au blé complet");
    Assert.assertEquals(nutritionInfoImpo.getNutritionScore(),10);
    Assert.assertEquals(nutritionInfoImpo.getClasse(),"Mangeable");
    Assert.assertEquals(nutritionInfoImpo.getColor(),"yellow");
  }

  @Test
  public void test2()
  {
    NutritionInformation nutritionInformation = new NutritionInformation();
    nutritionInformation = nutritionScoreService.calculateNutritionScore("8000500310427");
    NutritionInfoImpo nutritionInfoImpo = new NutritionInfoImpo();
    nutritionInfoImpo.setBarCode("8000500310427 ");
    nutritionInfoImpo.setName(nutritionInformation.getProduct_name());
    nutritionInfoImpo.setClasse(nutritionInformation.getNutrition_grades());
    nutritionInfoImpo.setNutritionScore(nutritionInformation.getNutritionScore());
    nutritionInfoImpo.setColor(nutritionInformation.getColor());
    Assert.assertEquals(nutritionInfoImpo.getName(),"Ferrero- Nutella Biscuits Resealable Bag");
    Assert.assertEquals(nutritionInfoImpo.getNutritionScore(),9);
    Assert.assertEquals(nutritionInfoImpo.getClasse(),"Mangeable");
    Assert.assertEquals(nutritionInfoImpo.getColor(),"yellow");
  }

  @Test
  public void test3()
  {
    NutritionInformation nutritionInformation = new NutritionInformation();
    nutritionInformation = nutritionScoreService.calculateNutritionScore("5060150940002");
    NutritionInfoImpo nutritionInfoImpo = new NutritionInfoImpo();
    nutritionInfoImpo.setBarCode("5060150940002");
    nutritionInfoImpo.setName(nutritionInformation.getProduct_name());
    nutritionInfoImpo.setClasse(nutritionInformation.getNutrition_grades());
    nutritionInfoImpo.setNutritionScore(nutritionInformation.getNutritionScore());
    nutritionInfoImpo.setColor(nutritionInformation.getColor());
    Assert.assertEquals(nutritionInfoImpo.getName(),"Free Range Egg White");
    Assert.assertEquals(nutritionInfoImpo.getNutritionScore(),0);
    Assert.assertEquals(nutritionInfoImpo.getClasse(),"Bon");
    Assert.assertEquals(nutritionInfoImpo.getColor(),"light green");
  }

  @Test
  public void test4(){
    NutritionInformation nutritionInformation = new NutritionInformation();
    nutritionInformation = nutritionScoreService.calculateNutritionScore("7622210449283");
    NutritionInfoImpo nutritionInfoImpo = new NutritionInfoImpo();
    nutritionInfoImpo.setBarCode("7622210449283");
    nutritionInfoImpo.setName(nutritionInformation.getProduct_name());
    nutritionInfoImpo.setClasse(nutritionInformation.getNutrition_grades());
    nutritionInfoImpo.setNutritionScore(nutritionInformation.getNutritionScore());
    nutritionInfoImpo.setColor(nutritionInformation.getColor());

    NutritionInformation nutritionInformation2 = new NutritionInformation();
    nutritionInformation2 = nutritionScoreService.calculateNutritionScore("5060150940002");
    NutritionInfoImpo nutritionInfoImpo2 = new NutritionInfoImpo();
    nutritionInfoImpo2.setBarCode("5060150940002");
    nutritionInfoImpo2.setName(nutritionInformation2.getProduct_name());
    nutritionInfoImpo2.setClasse(nutritionInformation2.getNutrition_grades());
    nutritionInfoImpo2.setNutritionScore(nutritionInformation2.getNutritionScore());
    nutritionInfoImpo2.setColor(nutritionInformation2.getColor());
    panir.addProduit(nutritionInfoImpo);
    panir.addProduit(nutritionInfoImpo2);
    List<NutritionInfoImpo> productList_test = new ArrayList<NutritionInfoImpo>();
    productList_test.add(new NutritionInfoImpo("7622210449283", "Prince Chocolat biscuits au blé complet", 10, "Mangeable", "yellow"));
    productList_test.add(new NutritionInfoImpo("5060150940002", "Free Range Egg White", 0, "Bon", "light green"));
    Assert.assertEquals(panir.getProductList(),productList_test);
  }

}

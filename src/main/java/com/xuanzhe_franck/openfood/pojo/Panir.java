package com.xuanzhe_franck.openfood.pojo;

import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Panir {
  private List<NutritionInfoImpo> productList;
  public Panir(){
    productList = new ArrayList<NutritionInfoImpo>();
  }
  public void addProduit(NutritionInfoImpo nutritionInfoImpo){
    productList.add(nutritionInfoImpo);
  }

}

package com.xuanzhe_franck.openfood.Repository;

import com.xuanzhe_franck.openfood.pojo.NutritionScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionScoreRepository extends JpaRepository<NutritionScore, Long> {

}

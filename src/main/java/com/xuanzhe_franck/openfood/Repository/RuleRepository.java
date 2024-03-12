package com.xuanzhe_franck.openfood.Repository;

import com.xuanzhe_franck.openfood.pojo.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

}
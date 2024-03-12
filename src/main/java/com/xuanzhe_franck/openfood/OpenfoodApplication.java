package com.xuanzhe_franck.openfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.xuanzhe_franck.openfood")
@EnableJpaRepositories(basePackages = "com.xuanzhe_franck.openfood.Repository")
@EntityScan(basePackages = "com.xuanzhe_franck.openfood.pojo")
public class OpenfoodApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenfoodApplication.class, args);
  }

}

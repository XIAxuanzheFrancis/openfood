package com.xuanzhe_franck.openfood.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
      return new OpenAPI()
          .info(new Info().title("Api Documentation")
              .description("Api Documentation")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org"))
              .contact(new Contact().name("xiaxuanzhefrancis").url("https://github.com").email("3058634081@qq.com")))
          .externalDocs(new ExternalDocumentation()
              .description("SpringShop Wiki Documentation")
              .url("https://springshop.wiki.github.org/docs"));
    }
  }
//  @Bean
//  public Docket docket(){
//    return new Docket(DocumentationType.SWAGGER_2)
//        .select()
//        .apis(RequestHandlerSelectors.basePackage("com.xuanzhe_franck.openfood.controller"))
//        .paths(PathSelectors.any())
//        .build()
//        .apiInfo(apiInfo());
//  }
//
//  private ApiInfo apiInfo(){
//    Contact contact = new Contact("xiaxuanzhe","https://github.com","xiaxingba@gmail.com");
//    return new ApiInfo("Api Documentation",
//        "Api Documentation",
//        "1.0",
//        "urn:tos",
//        contact,
//        "Apache 2.0",
//        "http://www.apache.org/licenses/LICENSE-2.0",
//        new ArrayList());
//  }
package com.ddn.stock;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by chenzi on 5/31/2016.
 */
@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = {HypermediaAutoConfiguration.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Docket newsApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(apis())
        .build();
  }

  private Predicate<RequestHandler> apis() {
    return Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("ddn stock rest api")
        .description("ddn stock rest api")
        .version("0.0.1-SNAPSHOT")
        .build();
  }

}

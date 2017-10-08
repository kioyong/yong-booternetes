package com.yong.security.config;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by LiangYong on 2017/10/1.
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        log.info("start init swagger2");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("com.yong.security.controller"))
                .paths(regex(".*"))
                .build();

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("yong-orders Service API")
                .description("Follow APIs are for yong-orders oprations")
                .version("0.0.1-SNAPSHOT").build();
    }
}
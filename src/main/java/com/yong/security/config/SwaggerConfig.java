package com.yong.security.config;

import com.google.common.base.Predicate;
import com.yong.security.model.AuthenticationBean;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by LiangYong on 2017/10/1.
 */
@Configuration
@EnableSwagger2
@Slf4j
@Controller
public class SwaggerConfig {

    @Bean
    public Docket api() {
        log.info("start init swagger2");
        Parameter param = new ParameterBuilder()
                .parameterType("header")
                .name("Authorization")
                .description("Used for oauth authentication")
                .modelRef(new ModelRef("string"))
                .required(false)
                .build();
        List<Parameter> params = new ArrayList<>();
        params.add(param);
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yong.security.controller"))
                .paths(regex(".*"))
                .build()
                .globalOperationParameters(params);
    }
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("yong Security API")
                .description("Follow APIs are for yong_security operations")
                .version("0.0.1-SNAPSHOT").build();
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }

}
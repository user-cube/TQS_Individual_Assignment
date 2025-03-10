package pt.ua.tqs.airquality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("pt.ua.tqs.airquality")).paths(PathSelectors.any()).build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo("Air Quality and Weather Forecast",
                "A multi-layer web application, in Spring Boot, supplied with automated tests.", "1.0",
                "Terms of service", new Contact("Rui Coelho", "https://ruicoelho.pt/", "ruicoelho@ua.pt"),
                "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");
    }
}
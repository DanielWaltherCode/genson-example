package com.example.genson;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ResttemplateConfiguration {

    @Bean
    TestRestTemplate testRestTemplate() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        testRestTemplate
                .getRestTemplate()
                .setMessageConverters(List.of(new GensonMessageConverter<>("genson", Object.class)));
        return testRestTemplate;
    }
}

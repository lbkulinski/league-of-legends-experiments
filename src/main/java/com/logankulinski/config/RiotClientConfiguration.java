package com.logankulinski.config;

import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiotClientConfiguration {
    @Bean
    public R4J r4J(@Value("${riot.api-key}") String apiKey) {
        APICredentials apiCredentials = new APICredentials(apiKey);

        return new R4J(apiCredentials);
    }
}

package com.logankulinski.config;

import com.logankulinski.client.DataDragonClient;
import com.logankulinski.client.RiotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Objects;

@Configuration
public class HttpClientConfiguration {
    @Bean
    public DataDragonClient dataDragonClient() {
        String baseUrl = "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/en_US";

        RestClient restClient = RestClient.create(baseUrl);

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                                                                                 .build();

        return httpServiceProxyFactory.createClient(DataDragonClient.class);
    }

    @Bean
    public RiotClient riotClient(@Value("${riot.api-key}") String apiKey) {
        Objects.requireNonNull(apiKey);

        String baseUrl = "https://americas.api.riotgames.com";

        RestClient restClient = RestClient.builder()
                                          .baseUrl(baseUrl)
                                          .defaultHeader("X-Riot-Token", apiKey)
                                          .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                                                                                 .build();

        return httpServiceProxyFactory.createClient(RiotClient.class);
    }
}

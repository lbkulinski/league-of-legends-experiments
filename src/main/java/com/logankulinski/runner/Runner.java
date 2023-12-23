package com.logankulinski.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logankulinski.client.DataDragonClient;
import com.logankulinski.client.RiotClient;
import com.logankulinski.model.Account;
import com.logankulinski.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class Runner implements ApplicationRunner {
    private final DataDragonClient dataDragonClient;

    private final RiotClient riotClient;

    private final ObjectMapper objectMapper;

    @Autowired
    public Runner(DataDragonClient dataDragonClient, RiotClient riotClient, ObjectMapper objectMapper) {
        this.dataDragonClient = Objects.requireNonNull(dataDragonClient);

        this.riotClient = Objects.requireNonNull(riotClient);

        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = this.riotClient.getAccount("lbku", "lbku");

        String puuid = account.puuid();

        int start = 0;

        int count = 100;

        List<String> page = this.riotClient.getMatchIds(puuid, start, count);

        List<String> matchIds = new ArrayList<>();

        while (!page.isEmpty()) {
            matchIds.addAll(page);

            start += page.size();

            page = this.riotClient.getMatchIds(puuid, start, count);
        }

        String matchId = matchIds.getFirst();

        Match match = this.riotClient.getMatch(matchId);

        System.out.println(this.objectMapper.writeValueAsString(match));
    }
}

package com.logankulinski.client;

import com.logankulinski.model.Account;
import com.logankulinski.model.Match;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface RiotClient {
    @GetExchange("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    Account getAccount(@PathVariable String gameName, @PathVariable String tagLine);

    @GetExchange("/lol/match/v5/matches/by-puuid/{puuid}/ids")
    List<String> getMatchIds(@PathVariable String puuid, @RequestParam int start, @RequestParam int count);

    @GetExchange("/lol/match/v5/matches/{matchId}")
    Match getMatch(@PathVariable String matchId);
}

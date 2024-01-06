package com.logankulinski.runner;

import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public final class Runner implements ApplicationRunner {
    private final R4J r4J;

    private final String gameName;

    private final String tagLine;

    @Autowired
    public Runner(R4J r4J, @Value("${riot.game-name}") String gameName, @Value("${riot.tag-line}") String tagLine) {
        this.r4J = Objects.requireNonNull(r4J);

        this.gameName = Objects.requireNonNull(gameName);

        this.tagLine = Objects.requireNonNull(tagLine);
    }

    private Optional<MatchParticipant> getParticipant(LOLMatch match, String name) {
        return match.getParticipants()
                    .stream()
                    .filter(participant -> {
                        String summonerName = participant.getSummonerName();

                        return Objects.equals(summonerName, name);
                    })
                    .findAny();
    }

    @Override
    public void run(ApplicationArguments args) {
        RiotAccount account = this.r4J.getAccountAPI()
                                      .getAccountByTag(RegionShard.AMERICAS, this.gameName, this.tagLine);

        String puuid = account.getPUUID();

        List<String> matchIds = this.r4J.getLoLAPI()
                                        .getMatchAPI()
                                        .getMatchList(RegionShard.AMERICAS, puuid);

        Map<ZonedDateTime, List<Integer>> datesToDamages = new TreeMap<>();

        for (String matchId : matchIds) {
            LOLMatch match = this.r4J.getLoLAPI()
                                     .getMatchAPI()
                                     .getMatch(RegionShard.AMERICAS, matchId);

            Duration duration = match.getGameDurationAsDuration();

            if (duration.toMinutes() < 10) {
                continue;
            }

            System.out.println(match.getGameStartAsDate());

            Optional<MatchParticipant> me = this.getParticipant(match, "lbku");

            Optional<MatchParticipant> daniel = this.getParticipant(match, "Elektrode");

            Optional<MatchParticipant> jonathan = this.getParticipant(match, "Tokuro");

            Optional<MatchParticipant> jamie = this.getParticipant(match, "Tzymph");

            Optional<MatchParticipant> phi = this.getParticipant(match, "PhiMouse");

            if (me.isEmpty() || daniel.isEmpty() || jonathan.isEmpty() || jamie.isEmpty() || phi.isEmpty()) {
                continue;
            }

            ZonedDateTime gameStart = match.getGameStartAsDate();

            int myDamage = me.get()
                             .getTotalDamageDealtToChampions();

            int danielDamage = daniel.get()
                                     .getTotalDamageDealtToChampions();

            int jonathanDamage = jonathan.get()
                                         .getTotalDamageDealtToChampions();

            int jamieDamage = jamie.get()
                                   .getTotalDamageDealtToChampions();

            int phiDamage = phi.get()
                               .getTotalDamageDealtToChampions();

            datesToDamages.put(gameStart, List.of(myDamage, danielDamage, jonathanDamage, jamieDamage, phiDamage));
        }

        Path path = Path.of("src/main/resources/static/damage.csv");

        CSVFormat format = CSVFormat.DEFAULT.builder()
                                            .setHeader("Date", "Logan's Damage", "Daniel's Damage", "Jonathan's Damage", "Jamie's Damage", "Phi's Damage")
                                            .build();

        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter printer = new CSVPrinter(writer, format)) {
            datesToDamages.forEach((date, damage) -> {
                String dateString = date.format(DateTimeFormatter.ofPattern("yy-MM-dd H:mm"));

                int myDamage = damage.get(0);

                int danielDamage = damage.get(1);

                int jonathanDamage = damage.get(2);

                int jamieDamage = damage.get(3);

                int phiDamage = damage.get(4);

                try {
                    printer.printRecord(dateString, myDamage, danielDamage, jonathanDamage, jamieDamage, phiDamage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

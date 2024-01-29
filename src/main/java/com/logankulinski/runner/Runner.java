package com.logankulinski.runner;

import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Component
public final class Runner implements ApplicationRunner {
    private final R4J r4J;

    private final String gameName;

    private final String tagLine;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(Runner.class);
    }

    @Autowired
    public Runner(R4J r4J, @Value("${riot.game-name}") String gameName, @Value("${riot.tag-line}") String tagLine) {
        this.r4J = Objects.requireNonNull(r4J);

        this.gameName = Objects.requireNonNull(gameName);

        this.tagLine = Objects.requireNonNull(tagLine);
    }

    private int getParticipantDamage(LOLMatch match, String gameName) {
        Objects.requireNonNull(match);

        Objects.requireNonNull(gameName);

        MatchParticipant participant = match.getParticipants()
                                            .stream()
                                            .filter(matchParticipant -> {
                                                String summonerName = matchParticipant.getSummonerName();

                                                return Objects.equals(summonerName, gameName);
                                            })
                                            .findAny()
                                            .orElseThrow();

        return participant.getTotalDamageDealtToChampions();
    }

    private void saveData(Map<ZonedDateTime, Integer> datesToDamages) {
        Objects.requireNonNull(datesToDamages);

        Path path = Path.of("src/main/resources/damage.csv");

        CSVFormat format = CSVFormat.DEFAULT.builder()
                                            .setHeader("Date", "Total Damage Dealt to Champions")
                                            .build();

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
             CSVPrinter printer = new CSVPrinter(writer, format)) {
            datesToDamages.forEach((date, dpm) -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);

                String dateString = date.format(formatter);

                try {
                    printer.printRecord(dateString, dpm);
                } catch (IOException e) {
                    String message = e.getMessage();

                    Runner.LOGGER.error(message, e);
                }
            });
        } catch (IOException e) {
            String message = e.getMessage();

            Runner.LOGGER.error(message, e);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        Objects.requireNonNull(args);

        RiotAccount account = this.r4J.getAccountAPI()
                                      .getAccountByTag(RegionShard.AMERICAS, this.gameName, this.tagLine);

        String puuid = account.getPUUID();

        List<String> matchIds = this.r4J.getLoLAPI()
                                        .getMatchAPI()
                                        .getMatchList(RegionShard.AMERICAS, puuid);

        Map<ZonedDateTime, Integer> datesToDamages = new TreeMap<>();

        for (String matchId : matchIds) {
            LOLMatch match = this.r4J.getLoLAPI()
                                     .getMatchAPI()
                                     .getMatch(RegionShard.AMERICAS, matchId);

            ZonedDateTime gameStart = match.getGameStartAsDate();

            int participantDamage = this.getParticipantDamage(match, this.gameName);

            datesToDamages.put(gameStart, participantDamage);
        }

        this.saveData(datesToDamages);
    }
}

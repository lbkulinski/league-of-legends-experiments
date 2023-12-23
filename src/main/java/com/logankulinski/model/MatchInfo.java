package com.logankulinski.model;

import java.util.List;

public record MatchInfo(
    long gameCreation,

    long gameDuration,

    long gameEndTimestamp,

    long gameId,

    String gameMode,

    String gameName,

    long gameStartTimestamp,

    String gameType,

    String gameVersion,

    int mapId,

    List<Participant> participants,

    String platformId,

    int queueId,

    List<Team> teams,

    String tournamentCode
) {
}

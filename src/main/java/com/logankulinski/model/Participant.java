package com.logankulinski.model;

public record Participant(
    int assists,

    int baronKills,

    int bountyLevel,

    int champExperience,

    int champLevel,

    int championId,

    String championName,

    int championTransform,

    int consumablesPurchased,

    int damageDealtToBuildings,

    int damageDealtToObjectives,

    int damageDealtToTurrets,

    int damageSelfMitigated,

    int deaths,

    int detectorWardsPlaced,

    int doubleKills,

    int dragonKills,

    boolean firstBloodAssist,

    boolean firstBloodKill,

    boolean firstTowerAssist,

    boolean firstTowerKill,

    boolean gameEndedInEarlySurrender,

    boolean gameEndedInSurrender,

    int goldEarned,

    int goldSpent,

    String individualPosition,

    int inhibitorKills,

    int inhibitorTakedowns,

    int inhibitorsLost,

    int item0,

    int item1,

    int item2,

    int item3,

    int item4,

    int item5,

    int item6,

    int itemsPurchased,

    int killingSprees,

    int kills,

    String lane,

    int largestCriticalStrike,

    int largestKillingSpree,

    int largestMultiKill,

    int longestTimeSpentLiving,

    int magicDamageDealt,

    int magicDamageDealtToChampions,

    int magicDamageTaken,

    int neutralMinionsKilled,

    int nexusKills,

    int nexusTakedowns,

    int nexusLost,

    int objectivesStolen,

    int objectivesStolenAssists,

    int participantId,

    int pentaKills,

    Perks perks,

    int physicalDamageDealt,

    int physicalDamageDealtToChampions,

    int physicalDamageTaken,

    int profileIcon,

    String puuid,

    int quadraKills,

    String riotIdName,

    String riotIdTagline,

    String role,

    int sightWardsBoughtInGame,

    int spell1Casts,

    int spell2Casts,

    int spell3Casts,

    int spell4Casts,

    int summoner1Casts,

    int summoner1Id,

    int summoner2Casts,

    int summoner2Id,

    String summonerId,

    int summonerLevel,

    String summonerName,

    boolean teamEarlySurrendered,

    int teamId,

    String teamPosition,

    int timeCCingOthers,

    int timePlayed,

    int totalDamageDealt,

    int totalDamageDealtToChampions,

    int totalDamageShieldedOnTeammates,

    int totalDamageTaken,

    int totalHeal,

    int totalHealsOnTeammates,

    int totalMinionsKilled,

    int totalTimeCCDealt,

    int totalTimeSpentDead,

    int totalUnitsHealed,

    int tripleKills,

    int trueDamageDealt,

    int trueDamageDealtToChampions,

    int trueDamageTaken,

    int turretKills,

    int turretTakedowns,

    int turretsLost,

    int unrealKills,

    int visionScore,

    int visionWardsBoughtInGame,

    int wardsKilled,

    int wardsPlaced,

    boolean win
) {
}

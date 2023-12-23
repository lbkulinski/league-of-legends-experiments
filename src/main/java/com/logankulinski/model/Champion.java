package com.logankulinski.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record Champion(
    String version,

    String id,

    String key,

    String name,

    String title,

    String blurb,

    ChampionInfo info,

    ChampionImage image,

    List<String> tags,

    @JsonAlias("partype")
    String resourceType,

    ChampionStats stats
) {
}

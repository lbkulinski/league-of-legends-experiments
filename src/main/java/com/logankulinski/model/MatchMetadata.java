package com.logankulinski.model;

import java.util.List;

public record MatchMetadata(
    String dataVersion,

    String matchId,

    List<String> participants
) {
}

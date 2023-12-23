package com.logankulinski.model;

import java.util.List;

public record Team(
    List<Ban> bans,

    Objectives objectives,

    int teamId,

    boolean win
) {
}

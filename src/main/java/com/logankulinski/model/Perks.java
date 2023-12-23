package com.logankulinski.model;

import java.util.List;

public record Perks(
    PerkStats statPerks,

    List<PerkStyle> styles
) {
}

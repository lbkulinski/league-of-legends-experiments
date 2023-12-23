package com.logankulinski.model;

import java.util.List;

public record PerkStyle(
    String description,

    List<PerkStyleSelection> selections,

    int style
) {
}

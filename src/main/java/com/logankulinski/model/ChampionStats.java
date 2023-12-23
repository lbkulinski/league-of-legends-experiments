package com.logankulinski.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public record ChampionStats(
    BigDecimal hp,

    @JsonAlias("hpperlevel")
    BigDecimal hpPerLevel,

    BigDecimal mp,

    @JsonAlias("mpperlevel")
    BigDecimal mpPerLevel,

    @JsonAlias("movespeed")
    BigDecimal moveSpeed,

    BigDecimal armor,

    @JsonAlias("armorperlevel")
    BigDecimal armorPerLevel,

    @JsonAlias("spellblock")
    BigDecimal spellBlock,

    @JsonAlias("spellblockperlevel")
    BigDecimal spellBlockPerLevel,

    @JsonAlias("attackrange")
    BigDecimal attackRange,

    @JsonAlias("hpregen")
    BigDecimal hpRegen,

    @JsonAlias("hpregenperlevel")
    BigDecimal hpRegenPerLevel,

    @JsonAlias("mpregen")
    BigDecimal mpRegen,

    @JsonAlias("mpregenperlevel")
    BigDecimal mpRegenPerLevel,

    BigDecimal crit,

    @JsonAlias("critperlevel")
    BigDecimal critPerLevel,

    @JsonAlias("attackdamage")
    BigDecimal attackDamage,

    @JsonAlias("attackdamageperlevel")
    BigDecimal attackDamagePerLevel,

    @JsonAlias("attackspeed")
    BigDecimal attackSpeed,

    @JsonAlias("attackspeedperlevel")
    BigDecimal attackSpeedPerLevel
) {
}

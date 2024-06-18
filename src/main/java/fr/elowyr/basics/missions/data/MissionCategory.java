package fr.elowyr.basics.missions.data;

public enum MissionCategory {

    FARMING("Farming"),
    PVE("PvE"),
    CRAFT("Craft"),
    FISH("Fishing"),
    SELL("Vente"),

    ;
    private final String name;

    MissionCategory(String name) {
        this.name = name;
    }
}

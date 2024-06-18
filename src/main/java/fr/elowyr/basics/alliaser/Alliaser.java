package fr.elowyr.basics.alliaser;

public class Alliaser {
    private final String from;

    private final String to;

    public Alliaser(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }
}

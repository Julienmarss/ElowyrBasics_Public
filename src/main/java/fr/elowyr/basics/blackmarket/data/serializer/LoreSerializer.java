package fr.elowyr.basics.blackmarket.data.serializer;

import fr.elowyr.basics.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LoreSerializer {

    private List<String> defaultLore;
    private List<String> marketLore;
    private List<String> combinedLore;

    public LoreSerializer(List<String> defaultLore, List<String> marketLore) {
        this.defaultLore = defaultLore;
        this.marketLore = marketLore;
        List<String> combined = new ArrayList<>();
        combined.addAll(defaultLore);
        combined.addAll(Utils.colorAll(marketLore));

        this.combinedLore = combined;
    }

    public List<String> getDefaultLore() {
        return defaultLore;
    }

    public void setDefaultLore(List<String> defaultLore) {
        this.defaultLore = defaultLore;
    }

    public List<String> getMarketLore() {
        return marketLore;
    }

    public void setMarketLore(List<String> marketLore) {
        this.marketLore = marketLore;
    }

    public List<String> getCombinedLore() {
        return combinedLore;
    }

    public void setCombinedLore(List<String> combinedLore) {
        this.combinedLore = combinedLore;
    }
}

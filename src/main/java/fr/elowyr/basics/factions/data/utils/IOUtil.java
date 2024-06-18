package fr.elowyr.basics.factions.data.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.elowyr.basics.adapters.ItemStackAdapter;
import fr.elowyr.basics.factions.data.FactionData;
import org.bukkit.inventory.ItemStack;

public class IOUtil {

    private final Gson gson;

    public IOUtil() {
        this.gson = createGsonInstance();
    }

    public Gson createGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping();

        return gsonBuilder.create();
    }

    public String serialize(FactionData factionData) {
        return this.gson.toJson(factionData);
    }

    public FactionData deserialize(String json) {
        return this.gson.fromJson(json, FactionData.class);
    }
}
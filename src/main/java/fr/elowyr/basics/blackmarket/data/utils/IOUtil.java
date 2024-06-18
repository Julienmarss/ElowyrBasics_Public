package fr.elowyr.basics.blackmarket.data.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.elowyr.basics.blackmarket.data.BlackData;
import fr.elowyr.basics.blackmarket.data.adapters.EnchantmentAdapter;
import fr.elowyr.basics.blackmarket.data.adapters.ItemStackAdapter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class IOUtil {

    private Gson gson;

    public IOUtil() {
        this.gson = createGsonInstance();
    }


    public Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                .registerTypeAdapter(Enchantment.class, new EnchantmentAdapter())
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(BlackData blackData) {
        return this.gson.toJson(blackData);
    }

    public BlackData deserialize(String json) {
        return this.gson.fromJson(json, BlackData.class);
    }

    public Gson getGson() {
        return gson;
    }
}


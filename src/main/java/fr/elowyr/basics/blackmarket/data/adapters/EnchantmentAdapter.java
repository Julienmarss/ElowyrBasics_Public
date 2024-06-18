package fr.elowyr.basics.blackmarket.data.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;

public class EnchantmentAdapter extends TypeAdapter<Enchantment> {

    @Override
    public void write(JsonWriter out, Enchantment enchantment) throws IOException {
        if (enchantment == null) {
            out.nullValue();
            return;
        }
        out.value(enchantment.getName());
    }

    @Override
    public Enchantment read(JsonReader in) throws IOException {
        String name = in.nextString();
        return Enchantment.getByName(name);
    }
}
package fr.elowyr.basics.titles.data.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.elowyr.basics.titles.data.Title;

public class IOUtil {

    private final Gson gson;

    public IOUtil() {
        this.gson = createGsonInstance();
    }

    public Gson createGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping();

        return gsonBuilder.create();
    }

    public String serialize(Title title) {
        return this.gson.toJson(title);
    }

    public Title deserialize(String json) {
        return this.gson.fromJson(json, Title.class);
    }
}
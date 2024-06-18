package fr.elowyr.basics.factions.data.provider;


import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.factions.data.utils.IOUtil;
import fr.elowyr.basics.utils.FileUtils;
import fr.elowyr.basics.utils.io.provide.IProvider;
import fr.elowyr.basics.utils.io.readable.IReadable;
import fr.elowyr.basics.utils.io.writable.IWritable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactionProvider implements IProvider<String, FactionData>, IWritable, IReadable {

    private final ElowyrBasics elowyrBasics;
    private Map<String, FactionData> profiles;
    private File save;

    public FactionProvider(ElowyrBasics elowyrBasics) {
        this.elowyrBasics = elowyrBasics;
        this.profiles = new HashMap<>();
        this.save = new File(this.elowyrBasics.getDataFolder(), "/factions/");
    }


    @Override
    public void provide(String key, FactionData value) {
        this.profiles.put(key, value);
    }

    @Override
    public void remove(String key) {

        for (FactionData user : this.getProfiles()) {
            this.profiles.remove(key);
            final File file = new File(save, user.getFactionId() + ".json");
            FileUtils.deleteFile(file);
        }
    }

    @Override
    public FactionData get(String key) {
        return this.profiles.get(key);
    }

    @Override
    public void read() {

        File[] files = save.listFiles();

        if (files == null) {
            this.profiles = new HashMap<>();
            return;
        }
        IOUtil ioUtil = FactionManager.getInstance().getIoUtil();

        for (File file : files) {
            if (file.isFile()) {
                final String json = FileUtils.loadContent(file);
                FactionData user = ioUtil.deserialize(json);
                this.profiles.put(user.getFactionId(), user);
            }
        }

    }

    @Override
    public void write() {

        if (this.profiles == null) return;

        final IOUtil ioUtil = FactionManager.getInstance().getIoUtil();

        for (FactionData user : this.getProfiles()) {
            final File file = new File(save, user.getFactionId() + ".json");
            final String json = ioUtil.serialize(user);
            FileUtils.save(file, json);
        }
    }

    public void write(FactionData factionData) {

        if (this.profiles == null) return;

        final IOUtil ioUtil = FactionManager.getInstance().getIoUtil();

        final File file = new File(save, factionData.getFactionId() + ".json");
        final String json = ioUtil.serialize(factionData);
        FileUtils.save(file, json);
    }

    public Collection<FactionData> getProfiles() {
        return this.profiles.values();
    }
}

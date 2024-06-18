package fr.elowyr.basics.titles.data.provider;


import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.titles.data.utils.IOUtil;
import fr.elowyr.basics.utils.FileUtils;
import fr.elowyr.basics.utils.io.provide.IProvider;
import fr.elowyr.basics.utils.io.readable.IReadable;
import fr.elowyr.basics.utils.io.writable.IWritable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TitleProvider implements IProvider<String, Title>, IWritable, IReadable {

    private final ElowyrBasics elowyrBasics;
    private Map<String, Title> titles;
    private File save;

    public TitleProvider(ElowyrBasics elowyrBasics) {
        this.elowyrBasics = elowyrBasics;
        this.titles = new HashMap<>();
        this.save = new File(this.elowyrBasics.getDataFolder(), "/titles/");
    }


    @Override
    public void provide(String key, Title value) {
        this.titles.put(key, value);
    }

    @Override
    public void remove(String key) {

        for (Title user : this.getTitles()) {
            this.titles.remove(key);
            final File file = new File(save, user.getName() + ".json");
            FileUtils.deleteFile(file);
        }
    }

    @Override
    public Title get(String key) {
        return this.titles.get(key);
    }

    @Override
    public void read() {

        File[] files = save.listFiles();

        if (files == null) {
            this.titles = new HashMap<>();
            return;
        }
        IOUtil ioUtil = TitleManager.getInstance().getIoUtil();

        for (File file : files) {
            if (file.isFile()) {
                final String json = FileUtils.loadContent(file);
                Title user = ioUtil.deserialize(json);
                this.titles.put(user.getName(), user);
            }
        }

    }

    @Override
    public void write() {

        if (this.titles == null) return;

        final IOUtil ioUtil = TitleManager.getInstance().getIoUtil();

        for (Title user : this.getTitles()) {
            final File file = new File(save, user.getName() + ".json");
            final String json = ioUtil.serialize(user);
            FileUtils.save(file, json);
        }
    }

    public Collection<Title> getTitles() {
        return this.titles.values();
    }
}

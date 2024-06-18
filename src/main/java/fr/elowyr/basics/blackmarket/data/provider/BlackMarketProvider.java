package fr.elowyr.basics.blackmarket.data.provider;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blackmarket.data.BlackData;
import fr.elowyr.basics.blackmarket.data.utils.IOUtil;
import fr.elowyr.basics.utils.FileUtils;
import fr.elowyr.basics.utils.io.provide.IProvider;
import fr.elowyr.basics.utils.io.readable.IReadable;
import fr.elowyr.basics.utils.io.writable.IWritable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlackMarketProvider implements IProvider<String, BlackData>, IWritable, IReadable {

    private final BlackMarketManager blackMarketManager;
    private Map<String, BlackData> blackDataMap;
    private final File save;

    public BlackMarketProvider(BlackMarketManager blackMarketManager) {
        this.blackMarketManager = blackMarketManager;
        this.blackDataMap = new HashMap<>();
        this.save = new File(ElowyrBasics.getInstance().getDataFolder(), "/blackmarket");
    }

    @Override
    public void provide(String key, BlackData value) {
        this.blackDataMap.put(key, value);
    }

    @Override
    public void remove(String key) {

        for (BlackData user : this.getBlackDataMap()) {
            this.blackDataMap.remove(key);
            final File file = new File(save, user.getId() + ".json");
            FileUtils.deleteFile(file);
        }
    }

    @Override
    public BlackData get(String key) {
        return this.blackDataMap.get(key);
    }

    @Override
    public void read() {

        File[] files = save.listFiles();

        if (files == null) {
            this.blackDataMap = new HashMap<>();
            return;
        }
        IOUtil ioUtil = this.blackMarketManager.getIoUtil();

        for (File file : files) {
            if (file.isFile()) {
                final String json = FileUtils.loadContent(file);
                BlackData user = ioUtil.deserialize(json);
                this.blackDataMap.put(user.getId(), user);
            }
        }

    }

    @Override
    public void write() {

        if (this.blackDataMap == null) return;

        final IOUtil ioUtil = this.blackMarketManager.getIoUtil();

        for (BlackData user : this.getBlackDataMap()) {
            final File file = new File(save, user.getId() + ".json");
            final String json = ioUtil.serialize(user);
            FileUtils.save(file, json);
        }
    }

    public Collection<BlackData> getBlackDataMap() {
        return this.blackDataMap.values();
    }
}


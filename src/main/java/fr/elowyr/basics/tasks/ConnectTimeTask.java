package fr.elowyr.basics.tasks;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ConnectTimeTask implements Runnable {

    private HashMap<Player, Long> connectionTimes = new HashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("elowyrbasics.send-money")) {
                if (!connectionTimes.containsKey(player)) {
                    connectionTimes.put(player, System.currentTimeMillis());
                } else {
                    long connectTime = System.currentTimeMillis() - connectionTimes.get(player);
                    if (connectTime >= 600000) {
                        double money = ThreadLocalRandom.current().nextInt(5000, 10000);
                        Bukkit.getScheduler().runTask(ElowyrBasics.getInstance(), () -> VaultUtils.depositMoney(player, money));
                        Lang.send(player, "listeners.send-money", "money", String.format("%.2f", money));
                        connectionTimes.put(player, System.currentTimeMillis());
                    }
                }
            }
        }
    }
}

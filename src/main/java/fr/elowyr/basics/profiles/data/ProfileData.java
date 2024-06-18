package fr.elowyr.basics.profiles.data;

import com.google.common.collect.Lists;
import fr.elowyr.basics.gifts.data.Gift;
import fr.elowyr.basics.mod.ModManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProfileData {

    //DATA PRINCIPAL
    private UUID playerUUID;
    private String playerName;
    private Date dateJoinFaction;
    private List<ItemStack> expiredItems;

    private boolean blacklist;

    //ECONOMY
    private int points;
    private int spents;
    private int joyaux;

    //GIFT
    private List<Gift> gifts;
    private boolean toggleGifts;

    //VANISH
    private boolean vanish;

    //TITLE
    private String title;
    private List<String> buyedTitle;

    public ProfileData() {
        this.init();
    }

    public ProfileData(Player player) {
        this.playerUUID = player.getUniqueId();
        this.playerName = player.getName();
        this.dateJoinFaction = new Date();
        this.init();
    }

    public void init() {
        this.expiredItems = Lists.newArrayList();

        //ECONOMY
        this.points = 0;
        this.spents = 0;
        this.joyaux = 0;

        //GIFT
        this.gifts = new ArrayList<>();
        this.toggleGifts = false;

        //VANISH
        this.vanish = false;

        //TITLE
        this.buyedTitle = Lists.newArrayList();
        this.title = "";

        //OTHERS
        this.blacklist = false;
    }

    public void changeVanish(Player player, boolean newStatut) {
        this.setVanish(newStatut);

        List<UUID> vanishedPlayers = ModManager.getInstance().getVanished();
        if (newStatut) {
            vanishedPlayers.add(player.getUniqueId());
        } else {
            vanishedPlayers.remove(player.getUniqueId());
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (newStatut) {
                players.hidePlayer(player);
            } else {
                players.showPlayer(player);
            }
        }
    }

    public void addExpiredItem(ItemStack item) {
        this.expiredItems.add(item);
    }

    public void removeExpiredItem(ItemStack item) {
        this.expiredItems.remove(item);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Date getDateJoinFaction() {
        return dateJoinFaction;
    }

    public void setDateJoinFaction(Date dateJoinFaction) {
        this.dateJoinFaction = dateJoinFaction;
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public boolean isToggleGifts() {
        return toggleGifts;
    }

    public void setToggleGifts(boolean toggleGifts) {
        this.toggleGifts = toggleGifts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBuyedTitle() {
        return buyedTitle;
    }

    public void setBuyedTitle(List<String> buyedTitle) {
        this.buyedTitle = buyedTitle;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSpents() {
        return spents;
    }

    public void setSpents(int spents) {
        this.spents = spents;
    }

    public int getJoyaux() {
        return joyaux;
    }

    public void setJoyaux(int joyaux) {
        this.joyaux = joyaux;
    }

    public List<ItemStack> getExpiredItems() {
        return expiredItems;
    }

    public void setExpiredItems(List<ItemStack> expiredItems) {
        this.expiredItems = expiredItems;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }
}

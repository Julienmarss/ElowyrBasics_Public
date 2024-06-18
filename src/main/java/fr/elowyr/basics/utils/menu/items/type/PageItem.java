package fr.elowyr.basics.utils.menu.items.type;

import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import org.bukkit.Material;

public class PageItem extends VirtualItem {
  private int page;
  private boolean previous;

  public PageItem(int page) {
    this(page, "&6Page suivante: &f");
  }

  public PageItem(int page, boolean previous) {
    this(page, "&6Page précédente: &f");
  }

  public PageItem(int page, String value) {
    super((new ItemBuilder(Material.ARROW)).setName(Utils.color(value)).toItemStack());
    this.page = page;
  }

  public int getPage() {
    return this.page;
  }

  public boolean isPrevious() {
    return this.previous;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void setPrevious(boolean previous) {
    this.previous = previous;
  }
}

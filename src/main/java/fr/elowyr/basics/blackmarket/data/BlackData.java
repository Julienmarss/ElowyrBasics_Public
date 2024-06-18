package fr.elowyr.basics.blackmarket.data;

import fr.elowyr.basics.blackmarket.data.serializer.ItemSerializer;
import lombok.Data;

@Data
public class BlackData {

    private String id;
    private ItemSerializer itemSerializer;
    private int price;

    public BlackData(String id, ItemSerializer itemSerializer, int price) {
        this.id = id;
        this.itemSerializer = itemSerializer;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemSerializer getItemSerializer() {
        return itemSerializer;
    }

    public void setItemSerializer(ItemSerializer itemSerializer) {
        this.itemSerializer = itemSerializer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

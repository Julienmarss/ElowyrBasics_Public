package fr.elowyr.basics.utils.menu;

import fr.elowyr.basics.utils.menu.menus.VirtualGUI;

import java.util.ArrayList;
import java.util.List;

public class BoardContainer {
    private final VirtualGUI virtualGUI;
    private final List<Integer> slots = new ArrayList();

    public BoardContainer(VirtualGUI virtualGUI, int slotFrom, int length, int width) {
        this.virtualGUI = virtualGUI;
        int perimeter = length * width;
        int size = 0;
        int l = 0;
        int w = 0;

        while(perimeter != size) {
            this.slots.add(slotFrom + w + l);
            ++l;
            ++size;
            if (l == length) {
                l = 0;
                w += 9;
            }
        }

    }

    public BoardContainer addSlot(int... slots) {
        int[] var2 = slots;
        int var3 = slots.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int slot = var2[var4];
            this.slots.add(slot);
        }

        return this;
    }

    public BoardContainer fillOffSetY(int slotFrom, int length) {
        for(int i = 0; i < length; ++i) {
            slotFrom += 9;
            this.slots.add(slotFrom);
        }

        return this;
    }

    public BoardContainer fillOffSetX(int slotFrom, int length) {
        for(int i = 0; i < length; ++i) {
            this.slots.add(slotFrom + i);
        }

        return this;
    }

    public BoardContainer removeSlot(int... index) {
        int[] var2 = index;
        int var3 = index.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int i = var2[var4];
            this.slots.remove(i);
        }

        return this;
    }

    public List<Integer> getSlots() {
        return this.slots;
    }

    public VirtualGUI getVirtualGUI() {
        return this.virtualGUI;
    }
}


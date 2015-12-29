package com.github.texxel.items;

import com.github.texxel.items.api.Item;
import com.github.texxel.items.bags.Bag;
import com.github.texxel.items.bags.Slot;
import com.github.texxel.utils.Filter;

import java.io.Serializable;
import java.util.Comparator;

public final class ItemUtils {

    private static class ItemComparator implements Comparator<Item>, Serializable {
        private static final long serialVersionUID = -8845619396280747297L;

        static final ItemComparator instance = new ItemComparator();

        @Override
        public int compare( Item i1, Item i2 ) {
            int p1 = i1 == null ? EmptyItem.instance().getSortPriority() : i1.getSortPriority();
            int p2 = i2 == null ? EmptyItem.instance().getSortPriority() : i2.getSortPriority();
            return Integer.compare( p1, p2 );
        }

        private Object readResolve() {
            return instance;
        }
    }

    private static class SlotComparator implements Comparator<Slot>, Serializable {
        private static final long serialVersionUID = 5173834874533064203L;

        static final SlotComparator instance = new SlotComparator();

        @Override
        public int compare( Slot s1, Slot s2 ) {
            int p1 = s1 == null ? EmptyItem.instance().getSortPriority() : s1.getItem().getSortPriority();
            int p2 = s2 == null ? EmptyItem.instance().getSortPriority() : s2.getItem().getSortPriority();
            return Integer.compare( p1, p2 );
        }

        private Object readResolve() {
            return instance;
        }

    }

    /**
     * Gets a comparator that will correctly order items in an inventory. The comparator can be
     * serialized and accepts null items.
     * @return a comparator for items
     */
    public static Comparator<Item> getItemComparator() {
        return ItemComparator.instance;
    }

    /**
     * Gets a comparator that will correctly order slots in an inventory. The comparator can be
     * serialized and accepts null items
     * @return a comparator for slots
     */
    public static Comparator<Slot> getSlotComparator() {
        return SlotComparator.instance;
    }

    private static final Filter<Item> weaponFilter = new Filter<Item>() {
        private static final long serialVersionUID = 6970812110147707324L;

        @Override
        public boolean isAllowed( Item obj ) {
            return obj instanceof com.github.texxel.items.api.Weapon;
        }

        private Object readResolve() {
            return weaponFilter;
        }
    };

    private static final Filter<Item> goldFilter = new Filter<Item>() {
        private static final long serialVersionUID = 220761760976352720L;

        @Override
        public boolean isAllowed( Item obj ) {
            return obj instanceof Gold;
        }

        private Object readResolve() {
            return goldFilter;
        }
    };

    private static final Filter<Item> bagFilter = new Filter<Item>() {
        private static final long serialVersionUID = 220761760976352720L;

        @Override
        public boolean isAllowed( Item obj ) {
            return obj instanceof Bag;
        }

        private Object readResolve() {
            return bagFilter;
        }
    };

    /**
     * Gets a filter that selects only weapons
     * @return a weapon filter
     */
    public static Filter<Item> weaponFilter() {
        return weaponFilter;
    }

    /**
     * Gets a filter that selects only bags
     * @return a bag filter
     */
    public static Filter<Item> bagFilter() {
        return bagFilter;
    }

    /**
     * Gets a filter that selects only gold
     * @return a gold filter
     */
    public static Filter<Item> goldFilter() {
        return goldFilter;
    }


}

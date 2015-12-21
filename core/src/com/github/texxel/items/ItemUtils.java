package com.github.texxel.items;

import com.github.texxel.items.bags.Bag;
import com.github.texxel.utils.Filter;

public final class ItemUtils {

    private static final Filter<Item> weaponFilter = new Filter<Item>() {
        private static final long serialVersionUID = 6970812110147707324L;

        @Override
        public boolean isAllowed( Item obj ) {
            return obj instanceof com.github.texxel.items.weapons.Weapon;
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

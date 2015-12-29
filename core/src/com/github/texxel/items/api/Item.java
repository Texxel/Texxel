package com.github.texxel.items.api;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.items.EmptyItem;
import com.github.texxel.ui.Examinable;

import java.io.Serializable;


public interface Item extends Examinable, Serializable {

    /**
     * Tests if this item is empty. All items should return false here. The only exception is
     * {@link EmptyItem} and {@link Stackable} items which have 0 quantity. If the item is empty,
     * then it should be safe to remove the item from any list of items.
     * @return true if the item is empty
     */
    boolean isEmpty();

    /**
     * Gets the texture of this item.
     * @return the items texture
     */
    TextureRegion getImage();

    /**
     * Gets the order that the items should appear in. This is used when sorting the contents of the
     * backpack and high numbers will appear at the top of the backpack. Texxel items have the
     * following priorities:
     * <dl>
     *     <dt>Weapons</dt>   <dd>500</dd>
     *     <dt>Armor</dt>     <dd>450</dd>
     *     <dt>Potions</dt>   <dd>300</dd>
     *     <dt>Scrolls</dt>   <dd>250</dd>
     *     <dt>Wands</dt>     <dd>200</dd>
     *     <dt>Rings</dt>     <dd>150</dd>
     *     <dt>Seeds</dt>     <dd>100</dd>
     *     <dt>Food</dt>      <dd>50</dd>
     *     <dt>Misc</dt>      <dd>0</dd>
     *     <dt>Bag</dt>       <dd>-50</dd>
     *     <dt>EmptyItem</dt> <dd>-100</dd>
     *     <dt>Gold</dt>      <dd>-150</dd>
     * </dl>
     * @return the sorting order
     */
    int getSortPriority();

}

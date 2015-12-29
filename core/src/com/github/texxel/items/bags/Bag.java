package com.github.texxel.items.bags;

import com.github.texxel.items.api.Item;
import com.github.texxel.utils.Filter;

public interface Bag extends Item {

    Filter<Item> getTypeFilter();

}

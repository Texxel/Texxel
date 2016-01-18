package com.github.texxel.items.api;

import com.github.texxel.mechanics.attacking.Effect;
import com.github.texxel.utils.Range;

import java.util.List;

public interface Armor {

    Range damageReduction();

    List<Effect> effects();

}

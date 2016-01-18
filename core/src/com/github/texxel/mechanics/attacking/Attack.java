package com.github.texxel.mechanics.attacking;

import com.github.texxel.actors.Char;
import com.github.texxel.utils.Range;

import java.util.List;

public interface Attack {

    Range accuracy();

    Range delay();

    Range damage();

    Char attacker();

    List<Effect> effects();

}

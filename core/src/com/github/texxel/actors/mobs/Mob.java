package com.github.texxel.actors.mobs;

import com.github.texxel.actors.Char;
import com.github.texxel.sprites.api.MobVisual;

public interface Mob extends Char {

    @Override
    MobVisual getVisual();

}

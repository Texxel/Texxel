package com.github.texxel.actors.heroes;

import com.github.texxel.actors.Char;
import com.github.texxel.sprites.api.HeroVisual;

public interface Hero extends Char {

    @Override
    HeroFOV getVision();

    @Override
    HeroVisual getVisual();
}

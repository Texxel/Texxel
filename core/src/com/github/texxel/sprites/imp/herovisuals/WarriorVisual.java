package com.github.texxel.sprites.imp.herovisuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WarriorVisual extends AbstractHeroVisual {

    private static final TextureRegion[][] frames = TextureRegion.split( new Texture( "warrior.png" ), 12, 15 );

    @Override
    protected TextureRegion[][] frames() {
        return frames;
    }
}

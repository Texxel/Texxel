package com.github.texxel.sprites.imp.herovisuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.api.CustomRenderer;

public class WarriorVisual extends AbstractHeroVisual implements CustomRenderer {

    private static final TextureRegion[][] frames = TextureRegion.split( new Texture( "game/warrior.png" ), 12, 15 );

    public WarriorVisual() {
        super( 12, 15 );
    }

    @Override
    protected TextureRegion[][] frames() {
        return frames;
    }

    @Override
    public boolean render( Batch batch ) {
        return true;
    }
}

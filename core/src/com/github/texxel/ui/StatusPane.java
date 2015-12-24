package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.scenes.GameScene;
import com.github.texxel.sprites.api.HeroVisual;

/**
 * The Status panel is the panel that sits at the top of the screen. It consists of the little hero
 * avatar, health bar, xp level, current depth, and the amount of keys in the backpack.
 */
public class StatusPane extends Table {

    public static final Texture texture = new Texture( "status_pane.png" );
    public static final TextureRegion heroRegion = new TextureRegion( texture, 80, 64 );
    public static final TextureRegion fillRegion = new TextureRegion( texture, 80, 0, 4, 64 );
    public static final TextureRegion statusRegion = new TextureRegion( texture, 84, 0, 128-84, 64 );

    private static final float HEIGHT = 30;

    /**
     * Constructs a Status panel about the hero in the given game scene
     * @param scene the scene to get info about the hero from
     */
    public StatusPane( GameScene scene ) {
        setFillParent( true );
        top();
        left();

        add( new HeroPanel( scene ) ).size( HEIGHT * heroRegion.getRegionWidth() / heroRegion.getRegionHeight(), HEIGHT );
        add( new Image( fillRegion ) ).height( HEIGHT ).expandX();
        add( new StatusPanel() ).size( HEIGHT * statusRegion.getRegionWidth() / statusRegion.getRegionHeight(), HEIGHT );
    }

    static class HeroPanel extends Image {

        private static final float AVATAR_SIZE = 0.7f * HEIGHT;
        private final GameScene scene;

        HeroPanel( GameScene scene ) {
            super( heroRegion );
            if ( scene == null )
                throw new NullPointerException( "'scene' cannot be null" );
            this.scene = scene;
        }

        @Override
        public void draw( Batch batch, float parentAlpha ) {
            super.draw( batch, parentAlpha );

            Hero hero = scene.getPlayer();
            HeroVisual visual = hero.getVisual();
            TextureRegion region = visual.getRegion();
            if ( hero.getHealth() < hero.getMaxHealth() * 0.9f )
                batch.setColor( 0.8f, 0.8f, 0.8f, 1 );
            float width = AVATAR_SIZE;
            float height = width * visual.height() / visual.width();
            if ( height > width ) {
                width *= width / height;
                height = AVATAR_SIZE;
            }
            batch.draw( region,
                    getX() + 0.4f, getY() + getHeight() - 0.4f - height,
                    width, height );
        }
    }

    class StatusPanel extends Image {
        StatusPanel() {
            super( statusRegion );
        }

        @Override
        public void draw( Batch batch, float parentAlpha ) {
            super.draw( batch, parentAlpha );
        }
    }

}

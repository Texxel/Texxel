package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.scenes.GameScene;
import com.github.texxel.sprites.api.HeroVisual;

/**
 * The Status panel is the panel that sits at the top of the screen. It consists of the little hero
 * avatar, health bar, xp level, current depth, and the amount of keys in the backpack.
 */
public class StatusPane extends Table {

    public static final NinePatchDrawable patch = new NinePatchDrawable(
            new NinePatch( new Texture( "ui/status_pane.png" ), 80, 42, 100, 1  ) );

    /**
     * Constructs a Status panel about the hero in the given game scene
     * @param scene the scene to get info about the hero from
     */
    public StatusPane( GameScene scene ) {
        setFillParent( true );
        top();
        left();
        setBackground( patch );

        add( new Avatar( scene ) );
        add( new StatusPanel() );
    }

    static class Avatar extends Actor {

        private static final float AVATAR_SIZE = 16;
        private static final float AVATAR_PAD  = 8;
        private final GameScene scene;

        Avatar( GameScene scene ) {
            if ( scene == null )
                throw new NullPointerException( "'scene' cannot be null" );
            this.scene = scene;
        }

        @Override
        public void draw( Batch batch, float parentAlpha ) {
            Hero hero = scene.getPlayer();
            HeroVisual visual = hero.getVisual();
            TextureRegion region = visual.getRegion();

            // color the hero a pretty shade when dying
            if ( hero.getHealth() < hero.getMaxHealth() * 0.9f )
                batch.setColor( 0.8f, 0.8f, 0.8f, 1 );

            float width = AVATAR_SIZE;
            float height = width * visual.height() / visual.width();
            // in-case the image is very tall, scale the avatar in the other direction
            if ( height > width ) {
                width *= width / height;
                height = AVATAR_SIZE;
            }

            batch.draw( region, AVATAR_PAD, getStage().getHeight()-AVATAR_SIZE-AVATAR_PAD, width, height );
        }
    }

    static class StatusPanel extends Widget {
        // TODO make status panel
    }

}

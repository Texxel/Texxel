package com.github.texxel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.goals.HeroHuntGoal;
import com.github.texxel.actors.ai.goals.HeroInteractGoal;
import com.github.texxel.actors.ai.goals.HeroPickUpGoal;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.items.Heap;
import com.github.texxel.levels.Level;
import com.github.texxel.scenes.GameScene;
import com.github.texxel.utils.Point2D;

/**
 * The HeroControl class lets the user interact with the hero. It listens to where the user taps the
 * screen and tells the Hero to venture there. It also listens in for keyboard input. It has logic
 * to make the hero pick up items, interact with tiles, and attack enemies.
 */
public class HeroControl extends InputListener {

    private final GameScene game;
    private final Vector3 vec3 = new Vector3();
    private final Vector2 vec2 = new Vector2();

    private final Vector2 touchDown = new Vector2();

    public HeroControl( final GameScene game ) {
        this.game = game;
    }

    @Override
    public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
        // ignore touches from non primary finger
        if ( pointer != 0 )
            return false;

        touchDown.set( event.getStageX(), event.getStageY() );

        return true;
    }

    @Override
    public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
        if ( pointer != 0 )
            return;

        // I think x,y is in actors coords - we want to use the scenes coords
        x = event.getStageX();
        y = event.getStageY();

        // make sure the finger wasn't moved very far
        float distance = touchDown.dst2( x, y );
        // TODO remove hardcoded cell selection distance
        if ( distance < 0.1f*0.1f ) {
            // in worlds coords
            vec3.set( x, y, 0 );

            // into screen coords
            Camera uiCamera = game.getUserInterface().getCamera();
            uiCamera.project( vec3 );
            // flip the y axis (project assumes y up but we want y down)
            vec3.y = Gdx.graphics.getHeight() - vec3.y - 1;

            // into world coords
            game.getGameCamera().unproject( vec3 );

            // select the cell
            onCellSelected( game.getPlayer(), (int) vec3.x, (int) vec3.y );
        }
    }

    @Override
    public boolean keyDown( InputEvent event, int keycode ) {
        Point2D from = game.getPlayer().getLocation();
        Point2D to = null;
        if ( keycode == Input.Keys.LEFT )
            to = from.plus( -1, 0 );
        if ( keycode == Input.Keys.RIGHT )
            to = from.plus( 1, 0 );
        if ( keycode == Input.Keys.UP )
            to = from.plus( 0, 1 );
        if ( keycode == Input.Keys.DOWN )
            to = from.plus( 0, -1 );

        if ( to != null )
            HeroControl.onCellSelected( game.getPlayer(), to.x, to.y );

        return true;
    }

    public static void onCellSelected( Hero hero, int x, int y ) {
        Level level = hero.level();

        for ( Char c : level.getCharacters()) {
            if ( c != hero && c.isOver( x, y ) ) {
                hero.setGoal( new HeroHuntGoal( hero, c ) );
                return;
            }
        }

        Point2D location = new Point2D( x, y );
        Heap heap = level.getHeaps().get( location );
        if ( heap != null ) {
            hero.setGoal( new HeroPickUpGoal( hero, location ) );
            return;
        }

        if ( x >= 0 && x < level.width() && y >= 0 && y < level.height() ) {
            hero.setGoal( new HeroInteractGoal( hero, new Point2D( x, y ) ) );
        }
    }

}

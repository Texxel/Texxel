package com.github.texxel.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.texxel.GameState;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.scenes.IntervalLevelScreen;
import com.github.texxel.tiles.PortalTile;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class DepthMover extends Actor {

    private final Game app;
    private final GameState state;

    public DepthMover( Game app, GameState state ) {
        this.app = app;
        this.state = state;
    }

    @Override
    public void act( float delta ) {
        Hero hero = state.getPlayer();
        Point2D location = hero.getLocation();
        Level level = hero.level();
        Tile tile = level.getTileMap().getTile( location.x, location.y );
        if ( tile instanceof PortalTile ) {
            int id = ( (PortalTile) tile ).getTargetLevel();
            LevelDescriptor target = state.getDungeon().getDescriptor( id );
            app.setScreen( new IntervalLevelScreen( app, state, target ) );
        }
    }
}

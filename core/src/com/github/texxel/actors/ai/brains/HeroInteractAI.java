package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.Interactable;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class HeroInteractAI extends HeroMoveAI {

    private static final long serialVersionUID = -1392186618004802182L;
    private final Char hero;
    private final Point2D target;

    public HeroInteractAI( Char hero, Point2D target ) {
        super( hero, target );
        this.hero = hero;
        this.target = target;
    }

    @Override
    public void update() {
        super.update();
        TileMap tileMap = hero.level().getTileMap();
        Tile tile = tileMap.getTile( target.x, target.y );
        if ( tile instanceof Interactable ) {
            Interactable interactable = (Interactable)tile;
            if ( interactable.canInteract( hero ) ) {
                interactable.interact( hero );
            }
        } else {
            hero.setBrain( new HeroIdleAI( hero ) );
        }
    }

}

package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.tiles.Interactable;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class HeroInteractAI extends HeroMoveAI {

    static {
        ConstructorRegistry.put( HeroInteractAI.class, new Constructor<HeroInteractAI>() {
            @Override
            public HeroInteractAI newInstance( Bundle bundle ) {
                return new HeroInteractAI( bundle );
            }
        } );
    }

    private Char hero;
    private Point2D target;

    public HeroInteractAI( Char hero, Point2D target ) {
        super( hero, target );
        this.hero = hero;
        this.target = target;
    }

    protected HeroInteractAI( Bundle bundle ) {
        super( bundle );
    }

    @Override
    public void update() {
        super.update();
        TileMap tileMap = hero.level().getTileMap();
        Tile tile = tileMap.getTile( target.x, target.y );
        if ( tile instanceof Interactable ) {
            Interactable interactable = (Interactable)tile;
            System.out.println( "Testing for interaction" );
            if ( interactable.canInteract( hero ) ) {
                System.out.println( "Interacting" );
                interactable.interact( hero );
            }
        } else {
            hero.setBrain( new HeroIdleAI( hero ) );
        }
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = super.bundle( topLevel );
        bundle.putBundlable( "hero", hero );
        bundle.putBundlable( "target", target );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        super.restore( bundle );
        hero = bundle.getBundlable( "hero" );
        target = bundle.getBundlable( "target" );
    }
}

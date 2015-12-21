package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.ChangeGoalAction;
import com.github.texxel.actors.ai.actions.HeroInteractAction;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.levels.Level;
import com.github.texxel.tiles.Interactable;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class HeroInteractGoal extends CharMoveGoal {

    private static final long serialVersionUID = 7420159028348502330L;

    public HeroInteractGoal( Char character, Point2D target ) {
        super( character, target );
    }

    @Override
    public Action onTargetReached() {
        Char hero = getCharacter();
        // no matter what happens, we want to change over to an HeroIdleGoal after the action finishes
        hero.setGoal( new HeroIdleGoal( hero ) );

        // now try to interact with the tile
        Level level = hero.level();
        Point2D target = getTarget();
        Tile tile = level.getTileMap().getTile( target.x, target.y );
        if ( tile instanceof Interactable ) {
            Interactable interactable = (Interactable) tile;
            if ( interactable.canInteract( hero ) ) {
                return new HeroInteractAction( hero, interactable );
            }
        }

        // no need to do anything
        return new IdleAction( hero, 0 );
    }

    @Override
    public Action onCannotReachTarget() {
        Char hero = getCharacter();
        return new ChangeGoalAction( hero, new HeroIdleGoal( hero ) );
    }
}

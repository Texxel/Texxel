package com.github.texxel.actors.heroes;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.levels.Level;
import com.github.texxel.sprites.api.HeroVisual;
import com.github.texxel.sprites.imp.herovisuals.WarriorVisual;
import com.github.texxel.utils.Point2D;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Warrior extends AbstractHero {

    private static final long serialVersionUID = -7409895294651892890L;

    private transient WarriorVisual sprite = new WarriorVisual();

    public Warrior( Level level, Point2D spawn ) {
        super( level, spawn );
        sprite.setLocation( spawn.x, spawn.y );
    }

    @Override
    public HeroVisual getVisual() {
        return sprite;
    }

    @Override
    public String name() {
        return "warrior";
    }

    @Override
    public String description() {
        return "me";
    }

    private void readObject( ObjectInputStream input )
            throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        sprite = new WarriorVisual();
    }

}
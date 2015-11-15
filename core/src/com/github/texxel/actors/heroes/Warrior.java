package com.github.texxel.actors.heroes;

import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.api.HeroVisual;
import com.github.texxel.sprites.imp.herovisuals.WarriorVisual;
import com.github.texxel.utils.Point2D;

public class Warrior extends AbstractHero {

    private static Constructor<Warrior> constructor = new Constructor<Warrior>() {
        @Override
        public Warrior newInstance( Bundle bundle ) { return new Warrior( bundle ); }

    };
    static {
        ConstructorRegistry.put( Warrior.class, constructor );
    }

    private WarriorVisual sprite = new WarriorVisual();

    public Warrior( Level level, Point2D spawn ) {
        super( level, spawn );
        sprite.setLocation( spawn.x, spawn.y );
    }

    protected Warrior( Bundle bundle ) {
        super( bundle );
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
}
package com.github.texxel.actors.mobs;

import com.github.texxel.levels.Level;
import com.github.texxel.sprites.api.MobVisual;
import com.github.texxel.sprites.imp.mobvisuals.RatVisual;
import com.github.texxel.utils.Point2D;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Rat extends AbstractMob {

    private static final long serialVersionUID = 3845310651972049210L;
    private transient RatVisual visual = new RatVisual();

    public Rat( Level level, Point2D spawn ) {
        super( level, spawn, 6 );
    }

    @Override
    public MobVisual getVisual() {
        return visual;
    }

    @Override
    public String name() {
        return "rat";
    }

    @Override
    public String description() {
        return "EEK! A icky rat!";
    }

    private void readObject( ObjectInputStream inputStream )
            throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        visual = new RatVisual();
    }

}

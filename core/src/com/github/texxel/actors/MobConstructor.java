package com.github.texxel.actors;

import com.github.texxel.levels.Level;
import com.github.texxel.utils.Point2D;

import java.io.Serializable;

public interface MobConstructor<T extends Char> extends Serializable {

    T newInstance( Level level, Point2D location );

}

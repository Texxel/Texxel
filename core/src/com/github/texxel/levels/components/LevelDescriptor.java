package com.github.texxel.levels.components;

import com.github.texxel.Dungeon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LevelDescriptor implements Serializable {

    private static final long serialVersionUID = 3231534207288622150L;

    private final Dungeon dungeon;
    private final int id;
    private int width = 32, height = 32;
    private LevelPlanner planner = new BasicPlanner();
    private ArrayList<LevelDecorator> decorators = new ArrayList<>();

    /**
     * Constructs a level constructor that will create a level in the given dungeon with the given id
     * @param dungeon the dungeon the level is in
     * @param id the levels id
     */
    public LevelDescriptor(Dungeon dungeon, int id) {
        this.dungeon = dungeon;
        this.id = id;
        decorators.add( new BasicDecorator() );
    }

    /**
     * Gets the dungeon the level is in
     * @return the dungeon
     */
    public Dungeon dungeon() {
        return dungeon;
    }

    /**
     * Gets the id of the level being built
     * @return the levels id
     */
    public int id() {
        return id;
    }


    /**
     * Gets the planned width of the level
     * @return the levels width
     */
    public int width() {
        return width;
    }

    /**
     * Gets the planned height of the level
     * @return the levels height
     */
    public int height() {
        return height;
    }

    /**
     * Sets the size that a level will be created at
     * @param width the levels new width
     * @param height the levels new height
     * @return this
     */
    public LevelDescriptor setSize( int width, int height ) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Gets the LevelPlanner that will generate the room layout
     * @return the level planner
     */
    public LevelPlanner getPlanner() {
        return planner;
    }

    /**
     * Sets the planner for the level
     * @param planner the levels new planner
     * @return this
     */
    public LevelDescriptor setPlanner( LevelPlanner planner ) {
        if ( planner == null )
            throw new NullPointerException( "'planner' cannot be null" );
        this.planner = planner;
        return this;
    }

    /**
     * Gets a list of all the decorators. Decorators can be added/removed as pleased. Do not add
     * null!
     * @return the decorators for this level
     */
    public List<LevelDecorator> getDecorators() {
        return decorators;
    }

}

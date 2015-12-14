package com.github.texxel.gameloop;

import com.github.texxel.levels.Level;

public interface GameUpdater {

    /**
     * Updates the game state a little bit more
     * @param level the level to update
     */
    void update( Level level );

    /**
     * Called
     */
    void quit();

}

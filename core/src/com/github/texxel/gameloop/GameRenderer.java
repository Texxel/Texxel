package com.github.texxel.gameloop;

import com.github.texxel.levels.Level;

public interface GameRenderer {

    void render( Level level );

    void resize( int width, int height );

}

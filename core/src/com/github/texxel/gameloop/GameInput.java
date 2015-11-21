package com.github.texxel.gameloop;

import com.github.texxel.levels.Level;

public interface GameInput {

    void process( Level level );

    void onDestroy();
}

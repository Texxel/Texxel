package com.github.texxel.modloader;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.GameState;

public interface Mod {

    String name();

    TextureRegion logo();

    String description();

    void onGameStart( GameState state );

}

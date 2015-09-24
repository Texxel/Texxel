package com.github.texxel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.texxel.scenes.GameScene;

public class Texxel extends Game {

	public static Texxel getInstance() {
		return instance;
	}
	private static Texxel instance;

	public Texxel() {
		instance = this;
	}

	@Override
	public void create() {
		super.setScreen( new GameScene() );
	}

	@Override
	public void render() {
		super.render();
		if ( Gdx.graphics.getFrameId() % 100 == 0 )
			System.out.println( "FPS: " + Gdx.graphics.getFramesPerSecond() );
	}
}

package com.github.texxel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.actors.heroes.Warrior;
import com.github.texxel.levels.Level;
import com.github.texxel.modloader.Mod;
import com.github.texxel.modloader.ModLoader;
import com.github.texxel.scenes.GameScene;

import java.util.List;

public class Texxel extends Game {

	public static Texxel getInstance() {
		return instance;
	}
	private static Texxel instance;

	private ModLoader loader;
	private List<Mod> modList;

	public Texxel( ModLoader loader ) {
		instance = this;
		this.loader = loader;
	}

	@Override
	public void create() {

		modList = loader.loadMods();

		Dungeon dungeon = new Dungeon( 1 );
		Level level = dungeon.loadLevel( dungeon.getDescriptor( 1 ) );
		Hero player = new Warrior( level, level.randomRespawnCell() );
        level.addActor( player );
		super.setScreen( new GameScene( dungeon, level, player ) );
		for ( Mod mod : modList ) {
			mod.onGameStart( dungeon );
		}
	}

	@Override
	public void render() {
		super.render();
		if ( Gdx.graphics.getFrameId() % 100 == 0 )
			System.out.println( "FPS: " + Gdx.graphics.getFramesPerSecond() );
	}
}

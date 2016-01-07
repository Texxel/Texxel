package com.github.texxel.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.texxel.modloader.Mod;
import com.github.texxel.modloader.ModLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DesktopModLoader implements ModLoader {

    @Override
    public List<Mod> loadMods() {
        List<Mod> mods = new ArrayList<>();

        FileHandle fileHandle = Gdx.files.local( "mods" );
        FileHandle[] plugins = fileHandle.list();

        System.out.println( fileHandle.file().getAbsolutePath() );
        for ( FileHandle file : plugins ) {
            try {
                URLClassLoader loader = new URLClassLoader( new URL[] { file.file().toURI().toURL() }, getClass().getClassLoader() );
                Class modClass = Class.forName( "com.github.texxel.modtest", true, loader );
                Mod mod = (Mod)modClass.newInstance();
                mods.add( mod );
            } catch ( MalformedURLException e ) {
                e.printStackTrace();
            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            } catch ( ClassCastException e ) {
                e.printStackTrace();
            }
        }

        return mods;
    }
}

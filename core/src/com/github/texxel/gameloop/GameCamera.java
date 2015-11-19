package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public class GameCamera extends OrthographicCamera implements Bundlable {

    static {
        ConstructorRegistry.put( GameCamera.class, new Constructor<GameCamera>() {
            @Override
            public GameCamera newInstance( Bundle bundle ) {
                return new GameCamera();
            }
        } );
    }

    public GameCamera() {
        super();
    }

    public GameCamera( float viewportWidth, float viewportHeight ) {
        super( viewportWidth, viewportHeight );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putDouble( "zoom",   zoom );
        bundle.putDouble( "width",  viewportWidth );
        bundle.putDouble( "height", viewportHeight );
        bundle.putDouble( "x",      position.x );
        bundle.putDouble( "y",      position.y );
        bundle.putDouble( "z",      position.z );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        float width = (float)bundle.getDouble( "width" );
        float height = (float)bundle.getDouble( "height" );
        float x = (float)bundle.getDouble( "x" );
        float y = (float)bundle.getDouble( "y" );
        float z = (float)bundle.getDouble( "z" );
        float zoom = (float)bundle.getDouble( "zoom" );

        this.viewportWidth = width;
        this.viewportHeight = height;
        this.position.set( x, y, z );
        this.zoom = zoom;
        update();
    }
}

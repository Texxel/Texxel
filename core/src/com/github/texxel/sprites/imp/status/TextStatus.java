package com.github.texxel.sprites.imp.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.github.texxel.sprites.api.CustomRenderer;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.utils.ColorMaths;

public class TextStatus extends StatusVisual implements CustomRenderer {

    private final String text;
    private final BitmapFont font;
    boolean started = false;

    public TextStatus( Visual parent, String text ) {
        super( parent, StatusVisual.Behaviour.HOVER );
        TextureRegion region = new TextureRegion( new Texture( "font1.png" ) );
        font = new BitmapFont( Gdx.files.internal( "font1.fnt" ), region );
        this.text = text;
    }

    @Override
    public boolean render( Batch batch ) {
        int color = getColor();
        font.getData().setScale( 0.2f );
        font.getColor().set( ColorMaths.ARGBtoRGBA( color ) );
        font.draw( batch, text, x(), y(), 1, Align.center, false );
        return false;
    }

    @Override
    public TextureRegion getRegion() {
        return EmptyTexture.instance();
    }

}

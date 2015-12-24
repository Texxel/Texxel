package com.github.texxel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * The pixel font class provides methods for getting top notch fonts at a specific size - all for
 * FREE :D
 */
public class PixelFont {

    private static final BitmapFont font8px, font10px, font12px, font14px, font19px;

    static {
        font8px  = new BitmapFont( Gdx.files.internal( "font8px.fnt" ) );
        font10px = new BitmapFont( Gdx.files.internal( "font10px.fnt" ) );
        font12px = new BitmapFont( Gdx.files.internal( "font12px.fnt" ) );
        font14px = new BitmapFont( Gdx.files.internal( "font14px.fnt" ) );
        font19px = new BitmapFont( Gdx.files.internal( "font19px.fnt" ) );
    }

    /**
     * Gets the font that is best used for drawing on the screen at a height of the the given amount
     * of screen pixels. The returned font is shared, so if any alterations are made to the font
     * when drawing with them, make sure that the changes are reset afterwards.
     * @param pixels the height of the font in screen pixels
     *          (from bottom of 'A' to the top of 'A')
     * @return the best font to use
     */
    public static BitmapFont getFontSized( float pixels ) {
        if ( pixels < 9 )
            return font8px;
        if ( pixels < 11 )
            return font10px;
        if ( pixels < 13 )
            return font12px;
        if ( pixels < 15 )
            return font14px;
        return font19px;
    }

    /**
     * The same as {@link #getFontSized(float)} but the units are given in world/camera units instead
     * of screen units.
     * @param units the size of the font in world units
     * @param camera the camera so scale with
     * @return the best font to use
     */
    public static BitmapFont getFontSized( float units, Camera camera ) {
        float cameraWidth = camera.viewportWidth;
        float screenWidth = Gdx.graphics.getWidth();
        return getFontSized( units * screenWidth / cameraWidth );
    }

}

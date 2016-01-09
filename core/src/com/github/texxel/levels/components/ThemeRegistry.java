package com.github.texxel.levels.components;

import com.github.texxel.sprites.TileAssets;
import com.github.texxel.utils.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The ThemeRegistry is a global registry of all the themes. Since themes cannot be Serialized
 * (because they contain textures), themes are referred to by a key. All themes then need to be
 * newly created and registered with this class every time the game is loaded. Any alterations to
 * existing Themes will also need to be made whenever the game is loaded.
 */
public final class ThemeRegistry {

    /**
     * A registry of all level themes
     */
    private static final HashMap<String, Theme> themes = new HashMap<>();
    private static final Map<String, Theme> publicThemes = Collections.unmodifiableMap( themes );

    /**
     * The default theme to used by level. This should not have anything added to it
     */
    public static final String DEFAULT = "default";

    /**
     * The sewers theme's key
     */
    public static final String SEWERS = "sewers";

    /**
     * The prison theme's key
     */
    public static final String PRISON = "prison";

    /**
     * The caves theme's key
     */
    public static final String CAVES = "caves";

    /**
     * The city theme's key
     */
    public static final String CITY = "city";

    /**
     * The halls theme's key
     */
    public static final String HALLS = "halls";

    static {
        themes.put( DEFAULT, new Theme() );
        themes.put( SEWERS, TileAssets.generate( TileAssets.SEWER_TEXTURE ) );
        themes.put( PRISON, TileAssets.generate( TileAssets.PRISON_TEXTURE ) );
        themes.put( CAVES,  TileAssets.generate( TileAssets.CAVES_TEXTURE  ) );
        themes.put( CITY,   TileAssets.generate( TileAssets.CITY_TEXTURE   ) );
        themes.put( HALLS,  TileAssets.generate( TileAssets.HALLS_TEXTURE  ) );
    }

    /**
     * Registers a theme with a key.
     * @param key the key to register into
     * @param theme the theme to register. Null to delete a theme
     */
    public static void register( String key, Theme theme ) {
        Assert.nonnull( key, "Theme's key cannot be null" );
        if ( theme == null )
            themes.remove( key );
        else
            themes.put( key, theme );
    }

    /**
     * Gets the theme registered with a key. If there is no theme registered with the key, then an
     * empty theme is returned (i.e. the tiles default textures will be used)
     * @param key the key to get
     * @return the theme. Never null
     */
    public static Theme get( String key ) {
        Theme theme = themes.get( key );
        if ( theme == null )
            return new Theme();
        else
            return theme;
    }

    /**
     * Gets an unmodifiable version of all the themes
     * @return the themes registry
     */
    public static Map<String, Theme> registry() {
        return publicThemes;
    }

    private ThemeRegistry() {
    }

}

package com.github.texxel.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The ItemSpriteSheet maintains all the texture regions for all the standard texxel images.
 * Alterations to the textures here will be reflected in all visuals using the sprite
 */
public class ItemSpriteSheet {

    /**
     * The image that all the items are drawn on
     */
    public static final Texture ITEM_SHEET_TEXTURE = new Texture( "game/items.png" );

    public static final TextureRegion BLANK_WEAPON      = make(  0,  0 );
    public static final TextureRegion BLANK_ARMOR       = make(  1,  0 );
    public static final TextureRegion BLANK_RING        = make(  2,  0 );
    public static final TextureRegion QUESTION_MARK     = make(  3,  0 );
    public static final TextureRegion GOLD              = make(  4,  0 );

    public static final TextureRegion SHORT_SWORD       = make(  0,  1 );
    public static final TextureRegion KNUCKLES          = make(  1,  1 );
    public static final TextureRegion DAGGER            = make(  2,  1 );
    public static final TextureRegion MACE              = make(  3,  1 );
    public static final TextureRegion STAFF             = make(  4,  1 );
    public static final TextureRegion SPEAR             = make(  5,  1 );
    public static final TextureRegion SWORD             = make(  6,  1 );
    public static final TextureRegion LONG_SWORD        = make(  7,  1 );
    public static final TextureRegion BATTLE_AXE        = make(  8,  1 );
    public static final TextureRegion WAR_HAMMER        = make(  9,  1 );
    public static final TextureRegion GLAIVE            = make( 10,  1 );

    public static final TextureRegion DART              = make(  0,  2 );
    public static final TextureRegion INCENDIARY_DART   = make(  1,  2 );
    public static final TextureRegion CURARE_DART       = make(  2,  2 );
    public static final TextureRegion JAVELIN           = make(  3,  2 );
    public static final TextureRegion SHURIKEN          = make(  4,  2 );
    public static final TextureRegion BOOMERANG         = make(  5,  2 );
    public static final TextureRegion TOMAHAWK          = make(  6,  2 );

    public static final TextureRegion CLOTH_ARMOR       = make(  0,  3 );
    public static final TextureRegion LEATHER_ARMOR     = make(  1,  3 );
    public static final TextureRegion MAIL_ARMOR        = make(  2,  3 );
    public static final TextureRegion SCALE_ARMOR       = make(  3,  3 );
    public static final TextureRegion PLATE_ARMOR       = make(  4,  3 );

    public static final TextureRegion WARRIOR_ARMOR     = make(  5,  3 );
    public static final TextureRegion MAGE_ARMOR        = make(  6,  3 );
    public static final TextureRegion ROUGE_ARMOR       = make(  7,  3 );
    public static final TextureRegion HUNTRESS_ARMOR    = make(  8,  3 );

    public static final TextureRegion MAGIC_MISSILE     = make(  0,  4 );
    public static final TextureRegion HOLLY_WAND        = make(  1,  4 );
    public static final TextureRegion YEW_WAND          = make(  2,  4 );
    public static final TextureRegion EBONY_WAND        = make(  3,  4 );
    public static final TextureRegion CHERRY_WAND       = make(  4,  4 );
    public static final TextureRegion TEAK_WAND         = make(  5,  4 );
    public static final TextureRegion ROWAN_WAND        = make(  6,  4 );
    public static final TextureRegion WILLOW_WAND       = make(  7,  4 );
    public static final TextureRegion MAHOGANY_WAND     = make(  8,  4 );
    public static final TextureRegion BAMBOO_WAND       = make(  9,  4 );
    public static final TextureRegion PURPLE_HEART_WAND = make( 10,  4 );
    public static final TextureRegion OAK_WAND          = make( 11,  4 );
    public static final TextureRegion BIRCH_WAND        = make( 12,  4 );

    public static final TextureRegion GARNET_RING       = make(  0,  5 );
    public static final TextureRegion RUBY_RING         = make(  1,  5 );
    public static final TextureRegion QUARTZ_RING       = make(  2,  5 );
    public static final TextureRegion TOPAZ_RING        = make(  3,  5 );
    public static final TextureRegion EMERALD_RING      = make(  4,  5 );
    public static final TextureRegion ONYX_RING         = make(  5,  5 );
    public static final TextureRegion OPAL_RING         = make(  6,  5 );
    public static final TextureRegion TOURMALINE_RING   = make(  7,  5 );
    public static final TextureRegion SAPPHIRE_RING     = make(  8,  5 );
    public static final TextureRegion AMETHYST_RING     = make(  9,  5 );
    public static final TextureRegion AGATE_RING        = make( 10,  5 );
    public static final TextureRegion DIAMOND_RING      = make( 11,  5 );

    public static final TextureRegion SCROLL_YNGVI      = make(  0,  6 );
    public static final TextureRegion SCROLL_RADIO      = make(  1,  6 );
    public static final TextureRegion SCROLL_LAGUZ      = make(  2,  6 );
    public static final TextureRegion SCROLL_NAUDIZ     = make(  3,  6 );
    public static final TextureRegion SCROLL_GYFU       = make(  4,  6 );
    public static final TextureRegion SCROLL_SOWILO     = make(  5,  6 );
    public static final TextureRegion SCROLL_MANNAZ     = make(  6,  6 );
    public static final TextureRegion SCROLL_KAUNAN     = make(  7,  6 );
    public static final TextureRegion SCROLL_ISAZ       = make(  8,  6 );
    public static final TextureRegion SCROLL_BERKANAN   = make(  9,  6 );
    public static final TextureRegion SCROLL_ODAL       = make( 10,  6 );
    public static final TextureRegion SCROLL_TIWAZ      = make( 11,  6 );

    public static final TextureRegion TURQUOISE_POTION  = make(  0,  7 );
    public static final TextureRegion CRIMSON_POTION    = make(  1,  7 );
    public static final TextureRegion AZURE_POTION      = make(  2,  7 );
    public static final TextureRegion JADE_POTION       = make(  3,  7 );
    public static final TextureRegion GOLDEN_POTION     = make(  4,  7 );
    public static final TextureRegion MAGENTA_POTION    = make(  5,  7 );
    public static final TextureRegion CHARCOAL_POTION   = make(  6,  7 );
    public static final TextureRegion BISTRE_POTION     = make(  7,  7 );
    public static final TextureRegion AMBER_POTION      = make(  8,  7 );
    public static final TextureRegion IVORY_POTION      = make(  9,  7 );
    public static final TextureRegion SILVER_POTION     = make( 10,  7 );
    public static final TextureRegion INDIGO_POTION     = make( 11,  7 );

    public static final TextureRegion FIREBlOOM         = make(  0,  8 );
    public static final TextureRegion ICECAP            = make(  1,  8 );
    public static final TextureRegion SORROWMOSS        = make(  2,  8 );
    public static final TextureRegion DREAMWEED         = make(  3,  8 );
    public static final TextureRegion SUNGRASS          = make(  4,  8 );
    public static final TextureRegion EARTHROOT         = make(  5,  8 );
    public static final TextureRegion FADELEAF          = make(  6,  8 );
    public static final TextureRegion ROTBERRY          = make(  7,  8 );

    public static final TextureRegion FOOD_RATION       = make(  0,  9 );
    public static final TextureRegion PASTY             = make(  1,  9 );
    public static final TextureRegion OVERPRICED_FOOD   = make(  2,  9 );
    public static final TextureRegion MYSTERY_MEAT      = make(  3,  9 );
    public static final TextureRegion CHARGRILLED_MEAT  = make(  4,  9 );
    public static final TextureRegion FROZEN_MEAT       = make(  5,  9 );

    public static final TextureRegion CHEST             = make(  0, 10 );
    public static final TextureRegion LOCKED_CHEST      = make(  1, 10 );
    public static final TextureRegion CRYSTAL_CHEST     = make(  2, 10 );
    public static final TextureRegion GRAVE_STONE       = make(  3, 10 );
    public static final TextureRegion SKULL             = make(  4, 10 );

    public static final TextureRegion SILVER_KEY        = make(  0, 11 );
    public static final TextureRegion GOLDEN_KEY        = make(  1, 11 );
    public static final TextureRegion SKELETON_KEY      = make(  2, 11 );

    public static final TextureRegion SEEN_POUCH        = make(  0, 12 );
    public static final TextureRegion SCROLL_HOLDER     = make(  1, 12 );
    public static final TextureRegion WAND_HOLSTER      = make(  2, 12 );

    public static final TextureRegion DRIED_ROSE        = make(  0, 13 );
    public static final TextureRegion GIANT_RAT_SKULL   = make(  1, 13 );
    public static final TextureRegion CORPSE_DUST       = make(  2, 13 );
    public static final TextureRegion PICK_AXE          = make(  3, 13 );
    public static final TextureRegion DARK_GOLD_ORE     = make(  4, 13 );
    public static final TextureRegion DWARF_TOKEN       = make(  5, 13 );

    public static final TextureRegion ARCANE_STYLUS     = make(  0, 14 );
    public static final TextureRegion WEIGHTSTONE       = make(  1, 14 );
    public static final TextureRegion ARMOR_KIT         = make(  2, 14 );
    public static final TextureRegion TOME_OF_MASTERY   = make(  3, 14 );
    public static final TextureRegion TORCH             = make(  4, 14 );
    public static final TextureRegion AMULET_OF_YENDOR  = make(  5, 14 );
    public static final TextureRegion LLOYDS_BEACON     = make(  6, 14 );
    public static final TextureRegion ANKH              = make(  7, 14 );
    public static final TextureRegion DEW_VIAL          = make(  8, 14 );
    public static final TextureRegion DEW_DROP          = make(  9, 14 );

    /**
     * Constructs a new Texture region using the default item sprite sheet. The passed location is
     * the number of items from the edge (starting at 0,0 from top left). It is not in pixel coords.
     * @param x the x location
     * @param y the y location
     */
    private static TextureRegion make( int x, int y ) {
        return new TextureRegion( ITEM_SHEET_TEXTURE, x * 16, y * 16, 16, 16 );
    }

}

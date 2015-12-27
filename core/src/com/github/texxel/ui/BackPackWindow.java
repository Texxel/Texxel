package com.github.texxel.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.texxel.items.Item;
import com.github.texxel.items.ItemStack;
import com.github.texxel.items.bags.BackPack;
import com.github.texxel.items.bags.Slot;
import com.github.texxel.sprites.api.Visual;

/**
 * A window designed to display the hero's backpack. All the inner classes BackPackWindow have been
 * declared made static and can be used for any purpose. Note that the implementation of
 * {@link com.github.texxel.ui.BackPackWindow.SlotGrid} works best with backpacks that have a size
 * which is a multiple of either 4 or 3.
 */
public class BackPackWindow extends TabbedWindow {

    /**
     * The size of each slot
     */
    private static final float SLOT_SIZE = 24;
    /**
     * The margin around the slots
     */
    private static final float SLOT_MARGIN = 4;

    public BackPackWindow( BackPack backPack ) {
        super( "Backpack" );
        getContent().add( new SlotGrid( backPack ) );

        setDebug( true, true );
    }

    /**
     * A grid of slots. This is the main section of a BackPackWindow.
     */
    public static class SlotGrid extends Table {

        public SlotGrid( BackPack backPack ) {
            // decide on the width/height of the display
            int size = backPack.getSize();
            int width;
            if ( size % 4 == 0 ) {
                // best size is four wide
                width = 4;
            } else if ( size % 3 == 0 ) {
                // next optimal is 3 wide
                width = 3;
            } else {
                // just use four wide and have a half finished
                // row at the base
                width = 4;
            }

            for ( int i = 0; i < size; i++ ) {
                if ( i % width == 0 )
                    row();

                Slot slot = backPack.getContents().get( i );
                add( new SlotImage( slot ) ).size( SLOT_SIZE ).space( SLOT_MARGIN );
            }

        }
    }

    /**
     * A SlotImage is a single slot in a SlotGrid. When clicked on, it will produce an explanation
     * of the item (unless the item is empty).
     */
    public static class SlotImage extends Button {

        private final Image image;

        public SlotImage( Slot slot ) {
            super( PixelSkin.chrome(), "basic" );
            final ItemStack itemStack = slot.getItemStack();
            final Item item = itemStack.item();
            Visual visual = item.getVisual();
            System.out.println( item );
            TextureRegion region = visual.getRegion();
            image = new Image( region );
            image.setSize( SLOT_SIZE, 20 );
            image.setPosition( 0, 0 );

            int amount = itemStack.quantity();
            if ( amount > 1 ) {
                Label label = new Label( Integer.toString( amount ), PixelSkin.chrome() );
                add( new Stack( image, label ) );
            } else {
                add( new Stack( image ) );
            }
            addListener( new ChangeListener() {
                @Override
                public void changed( ChangeEvent event, Actor actor ) {
                    event.setBubbles( false );
                    System.out.println( "hello: " + itemStack );
                }
            } );
        }
    }

}

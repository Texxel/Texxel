package com.github.texxel.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.texxel.items.Item;
import com.github.texxel.items.ItemStack;
import com.github.texxel.items.bags.BackPack;
import com.github.texxel.items.bags.Slot;
import com.github.texxel.sprites.api.Visual;

public class BackPackDisplay extends Table {

    int width, height;

    public BackPackDisplay( BackPack backPack ) {
        // decide on the width/height of the display
        int size = backPack.getSize();
        if ( size % 4 == 0 ) {
            width = 4;
            height = size / 4;
        } else if ( size % 3 == 0 ) {
            width = 3;
            height = size / 3;
        } else {
            width = 4;
            height = size / 4 + 1;
        }

        for ( int i = 0; i < size; i++ ) {
            if ( i % width == 0 )
                row();

            Slot slot = backPack.getContents().get( i );
            add( new SlotImage( slot ) ).size( 64, 64 ).space( 16 );
        }

    }

    private static class SlotImage extends Button {

        private final Image image;

        public SlotImage( Slot slot ) {
            super( PixelSkin.chrome() );
            ItemStack itemStack = slot.getItemStack();
            final Item item = itemStack.item();
            Visual visual = item.getVisual();
            System.out.println( item );
            TextureRegion region = visual.getRegion();
            image = new Image( region );
            image.setSize( 200, 200 );
            image.setPosition( 0, 0 );

            int amount = itemStack.quantity();
            if ( amount > 1 ) {
                Label label = new Label( Integer.toString( amount ), PixelSkin.chrome() );
                add( new Stack( image, label ) );
            } else {
                add( new Stack( image ) );
            }
        }
    }
}
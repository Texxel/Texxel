package com.github.texxel.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A game window is a table that has a header, content and footer tables. These tables can be
 * populated in any way.
 */
public class GameWindow extends Table {

    private final Table header;
    private final Table content;
    private final Table footer;

    public GameWindow() {
        header = new Table();
        content = new Table();
        footer = new Table();

        add( header ).expandX();
        row();
        add( content ).expand();
        row();
        add( footer ).expandX();

        setBackground( PixelSkin.chrome().getDrawable( "default-window" ) );
        setFillParent( true );
        pad( 20 );
    }

    /**
     * Gets the header table. This is positioned at the top of the window
     * @return the header table
     */
    public Table getHeader() {
        return header;
    }

    /**
     * Gets the content table.
     * @return the contents table
     */
    public Table getContent() {
        return content;
    }

    public Table getFooter() {
        return footer;
    }

}

package com.github.texxel.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * A game window is a table with a bunch of other tables inside it. There is a main window which is
 * designed to hold all the info (accessed through {@link #getMain()}). The main window has three
 * sub tables: a header ({@link #getHeader()}), content ({@link #getContent()}) and a footer ({@link
 * #getFooter()}). These tables can be populated in any way desired but it is recommended to not
 * put anything else in either the main table or the root table (i.e. this table). The main table
 * will have the {@link PixelSkin#chrome} set by default with the "default-window" background. No
 * other tables will have any skin set.
 */
public class GameWindow extends Table {

    private final Table main;
    private final Table header;
    private final Table content;
    private final Table footer;

    public GameWindow() {
        main = new Table();
        header = new Table();
        content = new Table();
        footer = new Table();

        main.add( header ).expandX();
        main.row();
        main.add( content ).expand();
        main.row();
        main.add( footer ).expandX();

        main.setSkin( PixelSkin.chrome() );
        main.setBackground( "default-window" );

        add( main ).align( Align.center );
        setFillParent( true );
    }

    /**
     * Constructs the window and adds a heading and the given content
     * @param heading the heading. Cannot be null
     * @param content the content to add. May be null
     */
    public GameWindow( String heading, Actor content ) {
        this();
        getHeader().add( new Label( heading, PixelSkin.chrome() ) );
        if ( content != null )
            getContent().add( content );
    }

    public Table getMain() {
        return main;
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

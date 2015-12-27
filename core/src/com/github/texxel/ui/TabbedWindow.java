package com.github.texxel.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

/**
 * The tabbed window allows for switching panels. The Tabs are placed in the footer table. Whenever
 * a tab is switched, anything in the content window is removed and the new content is added.
 */
public class TabbedWindow extends GameWindow {

    List<Tab> tabs = new ArrayList<>();

    public TabbedWindow() {
    }

    /**
     * Constructs a TabbedWindow with a heading and the tabs already added
     * @param header the header
     * @param tabs the tabs
     */
    public TabbedWindow( String header, Tab ... tabs ) {
        super( header, null );
        for ( Tab tab : tabs ){
            addTab( tab );
        }
    }

    public TabbedWindow addTab( Tab tab ) {
        if ( tab == null )
            throw new NullPointerException( "'tab' cannot be null" );

        tabs.add( tab );
        tab.window = this;
        getFooter().add( tab ).align( Align.left );

        // if this is the first tab, auto make it the active tab
        if ( tabs.size() == 1 )
            tab.setActive();

        return this;
    }

    /**
     * Removes the tab
     * @param tab the tab to remove
     * @return this
     */
    public TabbedWindow remove( Tab tab ) {
        // first switch to another tab
        if ( tab.isActive() ) {
            int size = tabs.size();
            getContent().clear();
            for ( int i = 0; i < size; i++ ) {
                Tab other = tabs.get( i );
                if ( !other.equals( tab ) ) {
                    other.setActive();
                    break;
                }
            }
        }
        tab.remove();
        while ( tabs.remove( tab ) ) {
        }
        return this;
    }

    public static class Tab extends Button {

        private TabbedWindow window;
        private Actor content;
        private boolean active = false;

        /**
         * Constructs a Tab with the specified content
         * @param content the content of the tab (may be null)
         */
        public Tab( Actor content ) {
            super( PixelSkin.chrome(), "tabbed-unselected" );
            this.content = content;
        }

        /**
         * Constructs a Tab with some text automatically added
         * @param label the text to display on the tab
         * @param content the content to display (may be null)
         */
        public Tab( String label, Actor content ) {
            this( content );
            add( new TextField( label, PixelSkin.chrome() ) );
        }

        /**
         * tests if this tab is the active tab
         * @return true if this tab is displayed
         */
        public boolean isActive() {
            return active;
        }

        /**
         * Sets this tab to become the active tab
         * @throws IllegalStateException if the button has not been added to a window yet
         */
        public void setActive() {
            if ( active )
                return;
            if ( window == null )
                throw new IllegalStateException( "Cannot become active when not added to a window" );
            active = true;
            setStyle( PixelSkin.chrome().get( "tab-selected", ButtonStyle.class ) );

            // remove old content and add our content
            Table contentTable = window.getContent();
            contentTable.clear();
            contentTable.add( content );

            // un activate the other tabs
            List<Tab> tabs = window.tabs;
            for ( int i = 0; i < tabs.size(); i++ ) {
                Tab tab = tabs.get( i );
                tab.deactivate();
            }
        }

        private void deactivate() {
            if ( !active )
                return;
            active = false;
            setStyle( PixelSkin.chrome().get( "tab-unselected", ButtonStyle.class ) );
        }

        /**
         * Sets the content that will be displayed when this tab next becomes active
         * @param content the content to display (may be null)
         */
        public void setContent( Actor content ) {
            this.content = content;
        }

        /**
         * Gets the content that will be displayed
         * @return the tabs content
         */
        public Actor getContent() {
            return content;
        }

    }

}

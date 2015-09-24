package com.github.texxel.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class ExamineWindow extends WidgetGroup {

    public ExamineWindow( Examinable examinable ) {
        setWidth( 6 );
        BitmapFont font = new BitmapFont();
        TextButton b = new TextButton( "hello", new Skin() );
    }

}

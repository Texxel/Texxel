package com.github.texxel.saving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public final class BundleWriter {

    private BundleWriter() {
    }

    public static FileHandle file( String filename ) {
        return Gdx.files.local( filename );
    }

    public static void write( String file, BundleGroup content ) {
        write( file( file ), content );
    }

    public static void write( FileHandle file, BundleGroup content ) {
        file.writeString( content.toString(), false, "UTF-8" );
    }

    public static BundleGroup load( FileHandle file ) {
        String content = file.readString( "UTF-8" );
        return BundleGroup.loadGroup( content );
    }

    public static BundleGroup load( String file ) {
        return load( file( file ) );
    }

}

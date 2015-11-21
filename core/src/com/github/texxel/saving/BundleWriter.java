package com.github.texxel.saving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

public final class BundleWriter {

    private BundleWriter() {
    }

    public static FileHandle file( String filename ) {
        return Gdx.files.local( filename );
    }

    public static void write( FileHandle file, BundleGroup content ) {
        file.writeString( content.toString(), false, "UTF-8" );
    }

    public static BundleGroup load( FileHandle file ) throws IOException {
        String content = file.readString( "UTF-8" );
        return BundleGroup.loadGroup( content );
    }

}

package com.github.texxel.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.github.texxel.modloader.Mod;
import com.github.texxel.modloader.ModLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

public class AndriodModLoader implements ModLoader {

    private final Context context;

    public AndriodModLoader( Context context ) {
        this.context = context;
    }

    @Override
    public List<Mod> loadMods() {
        File dexDir = context.getDir( "dex", Context.MODE_PRIVATE );
        Intent intent = new Intent( "com.github.texxel.LOAD_MOD" );
        List<ResolveInfo> list = context.getPackageManager().queryBroadcastReceivers( intent, 0 );
        List<Mod> mods = new ArrayList<>();
        for (ResolveInfo info : list ) {
            String source = info.activityInfo.applicationInfo.sourceDir;
            String classname = info.activityInfo.name;
            try {
                ClassLoader cl = new DexClassLoader( source,
                        dexDir.getAbsolutePath(), null, context.getClassLoader() );
                Class<?> clazz = cl.loadClass( classname );
                Mod mod = (Mod) clazz.newInstance();
                mods.add( mod );
            } catch ( ClassNotFoundException e ) {
                Log.e( "ModLoader", "Couldn't find class " + classname, e );
            } catch ( ClassCastException e ) {
                Log.e( "ModLoader", "Couldn't cast " + classname + " to " + Mod.class, e );
            } catch ( InstantiationException e ) {
                Log.e( "ModLoader", "Couldn't make new instance of " + classname, e );
            } catch ( IllegalAccessException e ) {
                Log.e( "ModLoader", "Illegal access to " + classname, e );
            } catch ( Exception e ) {
                Log.e( "ModLoader", "Unknown error loading " + classname );
                e.printStackTrace();
            }
        }
        return mods;
    }
}

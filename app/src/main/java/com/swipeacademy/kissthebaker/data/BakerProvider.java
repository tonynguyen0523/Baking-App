package com.swipeacademy.kissthebaker.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;

/**
 * Created by tonyn on 7/8/2017.
 */
@ContentProvider(authority = BakerProvider.AUTHORITY, database = BakerDatabase.class)
public class BakerProvider {
    public static final String AUTHORITY = "com.swipeacademy.kissthebaker.data.BakerProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String RECIPES = "recipes";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path:paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
}

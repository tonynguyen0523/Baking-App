package com.swipeacademy.kissthebaker.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by tonyn on 8/5/2017.
 */
@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDatabase.class)
public final class RecipeProvider {

    private RecipeProvider(){
    }

    public static final String AUTHORITY = "com.swipeacademy.kissthebaker.data.RecipeProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String RECIPELIST = "list";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDatabase.RECIPE_LIST) public static class RecipeList {

        @ContentUri(
                path = Path.RECIPELIST,
                type = "vnd.android.cursor.dir/list",
                defaultSort = RecipeListColumns.RECIPE_ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.RECIPELIST);

        @InexactContentUri(
                path = Path.RECIPELIST + "/#",
                name = "RECIPE_LIST_ID",
                type ="vnd.android.cursor.item/list",
                whereColumn = RecipeListColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return  buildUri(Path.RECIPELIST, String.valueOf(id));
        }

        @InexactContentUri(
                path = Path.RECIPELIST + "/*",
                name = "RECIPE_NAME",
                type = "vnd.android.cursor.item/list",
                whereColumn = RecipeListColumns.RECIPE_NAME,
                pathSegment = 1)
        public static Uri withRecipeName(String name){
            return buildUri(Path.RECIPELIST, name);
        }
    }
}

package com.swipeacademy.kissthebaker.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by tonyn on 8/5/2017.
 */
@ContentProvider(authority = RecipeProvider.AUTHORITY,
    database = RecipeDatabase.class,
    packageName = "com.swipeacademy.kissthebaker.provider")
public final class RecipeProvider {
    private RecipeProvider(){

    }

    public static final String AUTHORITY = "com.swipeacademy.kissthebaker.RecipeProvider";

    static final Uri BASEE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String LISTS = "lists";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASEE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDatabase.Tables.LISTS) public static class Lists {

        @ContentUri(
                path = Path.LISTS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = RecipeListColumns.RECIPE + "ASC")
        public static final Uri CONTENT_URI = buildUri(Path.LISTS);

        @InexactContentUri(
                path = Path.LISTS + "/#",
                name = "LIST_ID",
                type ="vnd.android.cursor.item/list",
                whereColumn = RecipeListColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return  buildUri(Path.LISTS, String.valueOf(id));
        }
    }
}

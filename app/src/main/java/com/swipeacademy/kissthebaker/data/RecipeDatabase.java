package com.swipeacademy.kissthebaker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.Table;


/**
 * Created by tonyn on 7/8/2017.
 *
 */
@Database(
        fileName = RecipeDatabase.DATABASE_NAME,
        version = RecipeDatabase.VERSION,
        packageName = "com.swipeacademy.kissthebaker.provider")
public final class RecipeDatabase {

    private RecipeDatabase(){
    }

    public static final int VERSION = 1;

    public static final String DATABASE_NAME = "database.db";

    public static class Tables {
        @Table(RecipeListColumns.class) public static final String LISTS = "lists";
    }
}

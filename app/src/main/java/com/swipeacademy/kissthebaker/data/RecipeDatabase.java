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
@Database(version = RecipeDatabase.VERSION)
public final class RecipeDatabase {

    public RecipeDatabase(){
    }

    public static final int VERSION = 4;

        @Table(RecipeListColumns.class) public static final String RECIPE_LIST = "list";

        @Table(RecipeIngredientsColumns.class) public static final String RECIPE_INGREDIENTS = "recipe_ingredients";

}

package com.swipeacademy.kissthebaker;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.swipeacademy.kissthebaker.data.RecipeDatabase;
import com.swipeacademy.kissthebaker.data.RecipeListColumns;

/**
 * Created by tonyn on 8/8/2017.
 */

public class Utility {

    public static boolean recipeExist(Context context, int recipeId){

        SQLiteOpenHelper database = com.swipeacademy.kissthebaker.data.generated.RecipeDatabase.getInstance(context);
        SQLiteDatabase db = database.getReadableDatabase();
        long exist = DatabaseUtils.queryNumEntries(db, RecipeDatabase.RECIPE_LIST, RecipeListColumns.RECIPE_ID + " = " + recipeId, null);
        db.close();

        return exist > 0;
    }
}

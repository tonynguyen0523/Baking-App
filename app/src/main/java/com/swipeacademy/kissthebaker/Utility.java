package com.swipeacademy.kissthebaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.swipeacademy.kissthebaker.data.RecipeDatabase;
import com.swipeacademy.kissthebaker.data.RecipeListColumns;

/**
 * Created by tonyn on 8/8/2017.
 */

public class Utility {

    private static final String SAVED_INGREDIENT = "saved_ingredient";

    /**
     * Method to get saved recipe name
     *
     * @param context Context
     */
    public static String getSavedIngredientName(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(SAVED_INGREDIENT, "");
    }

    /**
     * Method to save recipe name
     *
     * @param context Context
     * @param recipeName Name of recipe to be saved
     */
    public static void setSavedIngredientName(Context context, String recipeName){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SAVED_INGREDIENT,recipeName);
        editor.apply();
    }

    /**
     * Method to heck if recipe is in database
     *
     * @param context Context
     * @param recipeId ID of recipe
     */
    public static boolean recipeExist(Context context, int recipeId){

        SQLiteOpenHelper database = com.swipeacademy.kissthebaker.data.generated.RecipeDatabase.getInstance(context);
        SQLiteDatabase db = database.getReadableDatabase();
        long exist = DatabaseUtils.queryNumEntries(db, RecipeDatabase.RECIPE_LIST, RecipeListColumns.RECIPE_ID + " = " + recipeId, null);
        db.close();

        return exist > 0;
    }
}

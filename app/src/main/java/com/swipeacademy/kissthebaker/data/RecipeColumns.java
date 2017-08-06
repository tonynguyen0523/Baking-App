package com.swipeacademy.kissthebaker.data;

import android.support.annotation.NonNull;

import net.simonvt.schematic.annotation.DataType;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by tonyn on 8/5/2017.
 */

public interface RecipeColumns {

    @DataType(TEXT) @NonNull String RECIPE = "recipe";
}

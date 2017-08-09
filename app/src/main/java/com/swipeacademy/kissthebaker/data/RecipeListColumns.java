package com.swipeacademy.kissthebaker.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.*;
import static net.simonvt.schematic.annotation.DataType.Type.*;

/**
 * Created by tonyn on 8/5/2017.
 */

public interface RecipeListColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull String RECIPE_ID = "recipe_id";
    @DataType(TEXT) @NotNull String RECIPE_NAME = "recipe_name";
    @DataType(INTEGER) @NotNull String SERVING_SIZE = "serving_size";

}

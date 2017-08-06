package com.swipeacademy.kissthebaker.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.*;
import static net.simonvt.schematic.annotation.DataType.Type.*;

/**
 * Created by tonyn on 8/5/2017.
 */

public interface RecipeListColumns extends RecipeColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String ID = "_id";

    String SERVINGS = "servings";
}

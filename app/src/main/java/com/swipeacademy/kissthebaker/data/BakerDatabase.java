package com.swipeacademy.kissthebaker.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by tonyn on 7/8/2017.
 *
 */
@Database(version = BakerDatabase.VERSION)
public class BakerDatabase {

    private BakerDatabase(){}

    public static final int VERSION = 1;

//    @Table(BakerContract.BakerColumns.class)
//    public static final String RECIPES = "recipes";
}

package com.swipeacademy.kissthebaker;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.swipeacademy.kissthebaker.Main.RecipeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyn on 8/6/2017.
 */

public class TestUtility {

    public List<RecipeResponse> getData(){

        Context context = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();

        Gson gson = new GsonBuilder().create();
        Type recipeListType = new TypeToken<ArrayList<RecipeResponse>>(){}.getType();
        List<RecipeResponse> rList = new ArrayList<>();

        AssetManager assetManager = context.getAssets();

        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("baking.json");
            Reader reader;
            reader = new InputStreamReader(inputStream, "UTF-8");
            rList = gson.fromJson(reader,recipeListType);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return rList;
    }

}

package com.swipeacademy.kissthebaker;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.swipeacademy.kissthebaker.BakingInstructions.InstructionsActivity;
import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.data.RecipeDatabase;
import com.swipeacademy.kissthebaker.data.RecipeListColumns;
import com.swipeacademy.kissthebaker.data.RecipeProvider;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNot.not;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private ArrayList<RecipeResponse.StepsBean> sList;
    private ArrayList<RecipeResponse.IngredientsBean> iList;
    private List<RecipeResponse> rList;
    private int position = 1;

    @Before
    public void setUp() throws Exception{
        getTargetContext().deleteDatabase("database.db");
        onView(withId(R.id.fav_toggle)).check(matches(isEnabled()));
    }

    @Rule
    public ActivityTestRule<InstructionsActivity> mActivityTextRule =
            new ActivityTestRule<InstructionsActivity>(InstructionsActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();

                    getData(targetContext);

                    Intent results = new Intent(targetContext,InstructionsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("recipeName",rList.get(position).getName());
                    bundle.putParcelableArrayList("ingredientsList",rList.get(position).getIngredients());
                    bundle.putParcelableArrayList("stepsList", rList.get(position).getSteps());
                    results.putExtras(bundle);
                    return results;
                }
            };

    @Test
    public void checkIfCorrectData(){

        String recipeName = rList.get(position).getName();
        onView((withId(R.id.instructions_recipe_name))).check(matches(withText(recipeName)));
    }

    @Test
    public void favoriteToggleClicked(){

        sList = rList.get(position).getSteps();
        iList = rList.get(position).getIngredients();

        String recipeName = rList.get(position).getName();

        onView((withId(R.id.fav_toggle))).perform(click());

        insertRecipe(recipeName);
    }

    public void insertRecipe(String recipeName){

        ContentValues cv = new ContentValues();
        cv.put(RecipeListColumns.RECIPE_NAME, recipeName);
        cv.put(RecipeListColumns.RECIPE_ID,1);
        cv.put(RecipeListColumns.SERVING_SIZE, 8);
        getTargetContext().getContentResolver().insert(RecipeProvider.RecipeList.CONTENT_URI, cv);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Cursor cursor = getTargetContext().getContentResolver()
                    .query(RecipeProvider.RecipeList.CONTENT_URI,
                            null,
                            null,
                            null,
                            null,
                            null);

            assertTrue("Error: No records returned", cursor.moveToFirst());

            int count = cursor.getCount();

            assertTrue("Error: wrong number of records.", count == 1);

            cursor.close();
        }
    }


    public void getData(Context context){

        Gson gson = new GsonBuilder().create();
        Type recipeListType = new TypeToken<ArrayList<RecipeResponse>>(){}.getType();

        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open("baking.json");
            Reader reader;
            reader = new InputStreamReader(inputStream, "UTF-8");
            rList = gson.fromJson(reader,recipeListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

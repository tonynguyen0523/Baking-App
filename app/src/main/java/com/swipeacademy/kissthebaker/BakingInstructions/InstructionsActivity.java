package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;
import com.swipeacademy.kissthebaker.Utility;
import com.swipeacademy.kissthebaker.data.RecipeIngredientsColumns;
import com.swipeacademy.kissthebaker.data.RecipeListColumns;
import com.swipeacademy.kissthebaker.data.RecipeProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.swipeacademy.kissthebaker.Main.RecipeResponse.IngredientsBean;

public class InstructionsActivity extends AppCompatActivity implements InstructionsFragment.InstructionCallBack {

    private static final String LOG_TAG = InstructionsActivity.class.getSimpleName();

    @BindView(R.id.instructions_toolbar)Toolbar toolbar;
    @BindView(R.id.instructions_recipe_name)TextView recipeName;
    @BindView(R.id.fav_toggle)ToggleButton toggleButton;

    private static final String DIRECTION_FRAGMENT_TAG = "DFT";
    public static final String ACTION_DATA_UPDATED = "com.swipeacademy.kisshebaker.ACTION_DATA_UPDATE";
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        final ArrayList<RecipeResponse.IngredientsBean> iList = bundle
                .getParcelableArrayList(getString(R.string.ingredientsList_key));
        final ArrayList<RecipeResponse.StepsBean> sList = bundle.
                getParcelableArrayList(getString(R.string.stepsList_Key));

        final String name = bundle.getString(getString(R.string.recipeName_key));
        final int recipeId = bundle.getInt(getString(R.string.recipeId));
        final int servingSize = bundle.getInt(getString(R.string.servingSize));
        final int position = bundle.getInt(getString(R.string.position));
        recipeName.setText(name);

        // Check if recipe exist in database to set toggle
        if(Utility.recipeExist(this,recipeId)){
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent recipeDataUpdated = new Intent(ACTION_DATA_UPDATED);

                if (toggleButton.isChecked()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            // Save recipe and ingredients to database.
                            ContentValues recipeValues = new ContentValues();
                            recipeValues.put(RecipeListColumns.RECIPE_NAME, name);
                            recipeValues.put(RecipeListColumns.RECIPE_ID, recipeId);
                            recipeValues.put(RecipeListColumns.SERVING_SIZE, servingSize);

                            ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(iList.size());
                            for (IngredientsBean ingredients : iList){
                                ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                                        RecipeProvider.RecipeIngredients.CONTENT_URI);
                                builder.withValue(RecipeIngredientsColumns.RECIPE_LIST_ID, recipeId);
                                builder.withValue(RecipeIngredientsColumns.INGREDIENT, ingredients.getIngredient());
                                builder.withValue(RecipeIngredientsColumns.MEASUREMENT, ingredients.getMeasure());
                                builder.withValue(RecipeIngredientsColumns.QUANTITY, ingredients.getQuantity());
                                batchOperations.add(builder.build());
                            }

                            try{
                                InstructionsActivity.this.getContentResolver().delete(RecipeProvider.RecipeIngredients.CONTENT_URI,null,null);
                                InstructionsActivity.this.getContentResolver().delete(RecipeProvider.RecipeList.CONTENT_URI,null,null);
                                Utility.setSavedIngredientName(InstructionsActivity.this,name);
                                getContentResolver().insert(RecipeProvider.RecipeList.CONTENT_URI, recipeValues);
                                InstructionsActivity.this.getContentResolver().applyBatch(RecipeProvider.AUTHORITY, batchOperations);
                                InstructionsActivity.this.sendBroadcast(recipeDataUpdated);
                            } catch(RemoteException | OperationApplicationException e){
                                Log.e(LOG_TAG, "Error applying batch insert", e);
                            }
                        }
                    }).start();


                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            // Delete recipe and ingredient in database
                            Utility.setSavedIngredientName(InstructionsActivity.this,"");
                            InstructionsActivity.this.getContentResolver().delete(RecipeProvider.RecipeIngredients.CONTENT_URI,null,null);
                            InstructionsActivity.this.getContentResolver().delete(RecipeProvider.RecipeList.CONTENT_URI,null,null);
                            InstructionsActivity.this.sendBroadcast(recipeDataUpdated);
                        }
                    }).start();
                }
            }
        });

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instructions_fragment_container, InstructionsFragment.newInstance(iList, sList))
                    .commit();
        } else {

        }

        // Check for two pane mode
        if(findViewById(R.id.instruction_direction_fragment_container) != null){
            mTwoPane = true;

            // In tablet mode set orientation to landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instruction_direction_fragment_container,
                            DirectionsFragment.newInstance(sList, -1),DIRECTION_FRAGMENT_TAG)
                    .commit();

        } else {
            mTwoPane = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDirectionSelected(ArrayList<RecipeResponse.StepsBean> sList, int position) {
         if(mTwoPane){
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.instruction_direction_fragment_container,
                             DirectionsFragment.newInstance(sList, position),DIRECTION_FRAGMENT_TAG)
                     .commit();
         } else {
             Intent intent = new Intent(this, DirectionsActivity.class)
                     .putParcelableArrayListExtra(getString(R.string.stepsList_Key), sList)
                     .putExtra(getString(R.string.stepPosition), position);
             startActivity(intent);
         }

    }
}

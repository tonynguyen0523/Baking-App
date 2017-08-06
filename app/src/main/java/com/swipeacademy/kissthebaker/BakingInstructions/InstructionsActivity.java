package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity implements InstructionsFragment.InstructionCallBack {

    @BindView(R.id.instructions_toolbar)Toolbar toolbar;
    @BindView(R.id.instructions_recipe_name)TextView recipeName;
    @BindView(R.id.fav_toggle)ToggleButton toggleButton;

    private static final String DIRECTION_FRAGMENT_TAG = "DFT";
    private boolean mTwoPane;
    private ArrayList<RecipeResponse.IngredientsBean> inList;

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

        String name = bundle.getString(getString(R.string.recipeName_key));
        recipeName.setText(name);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.instructions_fragment_container, InstructionsFragment.newInstance(iList,sList))
                .commit();

        if(findViewById(R.id.instruction_direction_fragment_container) != null){
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instruction_direction_fragment_container,
                            DirectionsFragment.newInstance(sList, -1),DIRECTION_FRAGMENT_TAG)
                    .commit();

        } else {
            mTwoPane = false;
        }
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

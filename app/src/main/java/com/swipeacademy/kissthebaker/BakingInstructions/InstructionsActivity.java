package com.swipeacademy.kissthebaker.BakingInstructions;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {

    @BindView(R.id.instructions_tabs)TabLayout tabs;
    @BindView(R.id.instructions_viewPager)ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        ArrayList<RecipeResponse.IngredientsBean> iList = bundle.getParcelableArrayList("ingredientsList");
        ArrayList<RecipeResponse.StepsBean> sList = bundle.getParcelableArrayList("stepsList");

        InstructionPagerAdapter instructionPagerAdapter = new InstructionPagerAdapter(getSupportFragmentManager(), iList, sList);
        tabs.setupWithViewPager(viewPager);
        viewPager.setAdapter(instructionPagerAdapter);
    }

        private static class InstructionPagerAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 2;
        private ArrayList<RecipeResponse.IngredientsBean> iList;
        private ArrayList<RecipeResponse.StepsBean> sList;
        private String[] tabs = {"Ingredients", "Steps"};

        public InstructionPagerAdapter(FragmentManager fm, ArrayList<RecipeResponse.IngredientsBean> iList, ArrayList<RecipeResponse.StepsBean>sList) {
            super(fm);
            this.iList = iList;
            this.sList = sList;
        }

        @Override
        public Fragment getItem(int position) {
            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(iList);
            BakingStepsFragment bakingStepsFragment = BakingStepsFragment.newInstance(sList);
            switch (position){
                case 0:
                    return ingredientsFragment;
                case 1:
                    return bakingStepsFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

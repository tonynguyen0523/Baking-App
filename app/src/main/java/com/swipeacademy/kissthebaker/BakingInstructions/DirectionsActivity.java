package com.swipeacademy.kissthebaker.BakingInstructions;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DirectionsActivity extends AppCompatActivity {

//    @BindView(R.id.directions_toolbar)Toolbar toolbar;
//    @BindView(R.id.direction_appBar)AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Bundle bundle = getIntent().getExtras();
        ArrayList<RecipeResponse.StepsBean> sList = bundle.getParcelableArrayList(getString(R.string.stepsList_Key));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.direction_fragment_container,DirectionsFragment.newInstance(sList,
                        bundle.getInt(getString(R.string.stepPosition))))
                .commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public void hideToolBar(boolean isFullScreen){
//        if(isFullScreen){
//            toolbar.setVisibility(View.GONE);
//            appBarLayout.setVisibility(View.GONE);
//        } else {
//            toolbar.setVisibility(View.VISIBLE);
//            appBarLayout.setVisibility(View.VISIBLE);
//        }
//    }
}

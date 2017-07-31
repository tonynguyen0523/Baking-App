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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        ArrayList<RecipeResponse.StepsBean> sList = bundle.getParcelableArrayList(getString(R.string.stepsList_Key));

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.direction_fragment_container, DirectionsFragment.newInstance(sList,
                            bundle.getInt(getString(R.string.stepPosition))))
                    .commit();
        }else {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.swipeacademy.kissthebaker.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.swipeacademy.kissthebaker.R;
import butterknife.ButterKnife;

/**
 * Created by tonyn on 8/5/2017.
 */
public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Refresh recipe list in case database was updated
        RecipeFragment fragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe);
        fragment.refreshList();
    }

}

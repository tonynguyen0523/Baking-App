package com.swipeacademy.kissthebaker.Main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.swipeacademy.kissthebaker.BakingInstructions.InstructionsActivity;
import com.swipeacademy.kissthebaker.BakingInstructions.InstructionsFragment;
import com.swipeacademy.kissthebaker.MySingleton;
import com.swipeacademy.kissthebaker.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == 10001) && (resultCode == 10001)){
            RecipeFragment fragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe);

            fragment.refreshList();
            Log.d("REFRESH", "refresh");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RecipeFragment fragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe);
        fragment.refreshList();
        Log.d("ONRESTART", "restart called");
    }
}

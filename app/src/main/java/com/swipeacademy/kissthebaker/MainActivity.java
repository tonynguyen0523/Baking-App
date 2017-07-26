package com.swipeacademy.kissthebaker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_recyclerView)RecyclerView mRecyclerView;

    private List<RecipeResponse> recipeResponseList;
    private RecipeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestRecipe(this);

    }


    public void requestRecipe(Context context){

        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        recipeResponseList = new ArrayList<>();

        StringRequest recipeRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                Type recipeListType = new TypeToken<ArrayList<RecipeResponse>>(){}.getType();
                recipeResponseList = gson.fromJson(response,recipeListType);
                adapter = new RecipeRecyclerAdapter(MainActivity.this,recipeResponseList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(adapter);
                Log.d("LOGTAG", Integer.toString(recipeResponseList.size()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(recipeRequest);
    }


}

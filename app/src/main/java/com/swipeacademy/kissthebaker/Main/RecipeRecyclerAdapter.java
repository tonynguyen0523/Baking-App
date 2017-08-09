package com.swipeacademy.kissthebaker.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.swipeacademy.kissthebaker.R;
import com.swipeacademy.kissthebaker.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyn on 7/25/2017.
 */

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {

    private List<RecipeResponse> recipeList;
    private Context context;
    private RecipeCardClickListener listener;

    public RecipeRecyclerAdapter(Context context, List<RecipeResponse> recipeList){
        this.context = context;
        this.recipeList = recipeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = recipeList.get(position).getName();
        int servings = recipeList.get(position).getServings() ;
        int recipeId = recipeList.get(position).getId();

        holder.name.setText(name);
        holder.servings.setText(context.getString(R.string.servings, servings));

        if(Utility.recipeExist(context,recipeId)){
            holder.frameLayout.setVisibility(View.VISIBLE);
        } else {
            holder.frameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)TextView name;
        @BindView(R.id.recipe_servings)TextView servings;
        @BindView(R.id.favorite_frame_layout)FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {
            listener.onRecipeCardClicked(view, getAdapterPosition());
        }
    }

    public interface RecipeCardClickListener{
        void onRecipeCardClicked(View view, int position);
    }

    public void setOnItemClickListener(RecipeCardClickListener listener){this.listener = listener;}
}

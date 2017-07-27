package com.swipeacademy.kissthebaker.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swipeacademy.kissthebaker.R;

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

        int servings = recipeList.get(position).getServings() ;

        holder.name.setText(recipeList.get(position).getName());
        holder.servings.setText(context.getString(R.string.servings, servings));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)TextView name;
        @BindView(R.id.recipe_servings)TextView servings;

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

package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyn on 7/26/2017.
 */

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder> {

    private ArrayList<RecipeResponse.IngredientsBean> iList;
    private Context context;

    public IngredientsRecyclerAdapter(Context context, ArrayList<RecipeResponse.IngredientsBean> iList){
        this.context = context;
        this.iList = iList;
    }

    @Override
    public IngredientsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_recycler_item,parent,false);
        return new IngredientsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsRecyclerAdapter.ViewHolder holder, int position) {

        String iName = iList.get(position).getIngredient();
        String iMeasure = iList.get(position).getMeasure();
        double iQuantity = iList.get(position).getQuantity();
        String ingredient = context.getString(R.string.ingredient,Double.toString(iQuantity),iMeasure,iName);

        holder.name.setText(ingredient);
//        holder.measure.setText(iMeasure);
//        holder.quantity.setText(Double.toString(iQuantity));
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name)TextView name;
//        @BindView(R.id.ingredient_quantity)TextView quantity;
//        @BindView(R.id.ingredient_measure)TextView measure;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }
}
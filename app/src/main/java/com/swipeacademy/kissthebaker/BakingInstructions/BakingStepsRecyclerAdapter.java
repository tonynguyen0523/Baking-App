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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyn on 7/26/2017.
 */

public class BakingStepsRecyclerAdapter extends RecyclerView.Adapter<BakingStepsRecyclerAdapter.ViewHolder> {

    private ArrayList<RecipeResponse.StepsBean> sList;
    private Context context;

    public BakingStepsRecyclerAdapter(Context context, ArrayList<RecipeResponse.StepsBean> sList){
        this.context = context;
        this.sList = sList;
    }

    @Override
    public BakingStepsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_steps_recycler_item,parent,false);
        return new BakingStepsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BakingStepsRecyclerAdapter.ViewHolder holder, int position) {

        String shortDesc = sList.get(position).getShortDescription();
        String desc = sList.get(position).getDescription();

        holder.shortDesc.setText(shortDesc);
        holder.desc.setText(desc);
}

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.baking_steps_shortDes)TextView shortDesc;
        @BindView(R.id.baking_steps_description)TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
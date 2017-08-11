package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyn on 7/26/2017.
 */

public class DirectionsRecyclerAdapter extends RecyclerView.Adapter<DirectionsRecyclerAdapter.ViewHolder> {

    private ArrayList<RecipeResponse.StepsBean> sList;
    private Context context;
    private InstructionClickListener listener;
    private int lastSelected = -1;


    public DirectionsRecyclerAdapter(Context context, ArrayList<RecipeResponse.StepsBean> sList){
        this.context = context;
        this.sList = sList;
    }

    @Override
    public DirectionsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_steps_recycler_item,parent,false);

        return new DirectionsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DirectionsRecyclerAdapter.ViewHolder holder, int position) {

        String shortDesc = sList.get(position).getShortDescription();

        holder.shortDesc.setText(context.getString(R.string.step,position,shortDesc));

        // Highlight selected item
        if(position == lastSelected) {
            holder.mRelativeLayout.setSelected(true);
        } else {
            holder.mRelativeLayout.setSelected(false);
        }
}

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.baking_steps_shortDes)TextView shortDesc;
        @BindView(R.id.baking_steps_relative_layout)
        RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {

            listener.onInstructionClicked(view,getAdapterPosition());
            lastSelected = getAdapterPosition();

            // Refresh list to set correct highlighted item
            notifyDataSetChanged();
        }
    }

    public interface  InstructionClickListener{
        void onInstructionClicked(View view, int position);
    }

    public void setOnInstructionClickListener(InstructionClickListener listener){
        this.listener = listener;
    }
}
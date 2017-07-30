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

public class InstructionsRecyclerAdapter extends RecyclerView.Adapter<InstructionsRecyclerAdapter.ViewHolder> {

    private ArrayList<RecipeResponse.StepsBean> sList;
    private Context context;
    private InstructionClickListener listener;

    public InstructionsRecyclerAdapter(Context context, ArrayList<RecipeResponse.StepsBean> sList){
        this.context = context;
        this.sList = sList;
    }

    @Override
    public InstructionsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_steps_recycler_item,parent,false);
        return new InstructionsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionsRecyclerAdapter.ViewHolder holder, int position) {

        String shortDesc = sList.get(position).getShortDescription();

        holder.shortDesc.setText(context.getString(R.string.step,position + 1,shortDesc));
}

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.baking_steps_shortDes)TextView shortDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {
            listener.onInstructionClicked(view,getAdapterPosition());
        }
    }

    public interface  InstructionClickListener{
        void onInstructionClicked(View view, int position);
    }

    public void  setOnInstructionClickListener(InstructionClickListener listener){
        this.listener = listener;
    }
}
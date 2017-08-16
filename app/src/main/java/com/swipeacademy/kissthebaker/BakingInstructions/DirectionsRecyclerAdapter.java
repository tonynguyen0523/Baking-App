package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
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

    /**
     * DirectionsRecyclerAdapter constructor
     */
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
    public void onBindViewHolder(final DirectionsRecyclerAdapter.ViewHolder holder, int position) {

        String shortDesc = sList.get(position).getShortDescription();
        String thumbnailUrl = sList.get(position).getThumbnailURL();

        holder.shortDesc.setText(context.getString(R.string.step,position,shortDesc));

        // Check if thumbnail url is empty,
        // if so hide thumbnail image view.
        if(!thumbnailUrl.isEmpty()) {
            Picasso.with(context).load(thumbnailUrl).into(holder.mThumbnail, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    holder.mThumbnail.setVisibility(View.GONE);

                }
            });
        } else {
            holder.mThumbnail.setVisibility(View.GONE);
        }

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
        @BindView(R.id.thumbnail)
        ImageView mThumbnail;

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

    /**
     * Click listener interface
     */
    public interface  InstructionClickListener{

        /**
         * Method for when item is clicked
         *
         * @param view ViewHolder view
         * @param position Position of item clicked
         */
        void onInstructionClicked(View view, int position);
    }

    /**
     * Custom click listener
     *
     * @param listener ClickListener
     */
    public void setOnInstructionClickListener(InstructionClickListener listener){
        this.listener = listener;
    }
}
package com.swipeacademy.kissthebaker.BakingInstructions;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tonyn on 7/27/2017.
 */

public class StepFragment extends Fragment {

    private static final String STEPS_LIST = "directions";
    private static final String POSITION = "position";

    @BindView(R.id.baking_steps_shortDes)TextView shortDesc;
//    @BindView(R.id.baking_steps_description)TextView desc;
//    @BindView(R.id.exoPlayer)SimpleExoPlayerView exoPlayerView;

    private Unbinder unbinder;
    private ArrayList<RecipeResponse.StepsBean> stepsList;
    private int position;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private SimpleExoPlayer exoPlayer;

    public static StepFragment newInstance(ArrayList<RecipeResponse.StepsBean> stepsList, int position){
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST, stepsList);
        args.putInt(POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepsList = getArguments().getParcelableArrayList(STEPS_LIST);
        position = getArguments().getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this,view);
        setRetainInstance(true);


        shortDescription = stepsList.get(position).getShortDescription();
        description = stepsList.get(position).getDescription();
        videoUrl = stepsList.get(position).getVideoURL();

        shortDesc.setText(shortDescription);
//        desc.setText(description);
//
//        exoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
//                getResources(), R.drawable.ic_play_circle_outline_white_48dp
//        ));

//        initializePlayer(Uri.parse(videoUrl));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        unbinder.unbind();
    }

//    private void initializePlayer(Uri mediaUri){
//        if(exoPlayer == null){
//            TrackSelector trackSelector = new DefaultTrackSelector();
//            LoadControl loadControl = new DefaultLoadControl();
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
//            exoPlayerView.setPlayer(exoPlayer);
//            String userAgent = Util.getUserAgent(getContext(),"KissTheBaker");
//            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
//                    getContext(),userAgent), new DefaultExtractorsFactory(),null,null);
//            exoPlayer.prepare(mediaSource);
//        }
//    }

    private void releasePlayer(){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }





}

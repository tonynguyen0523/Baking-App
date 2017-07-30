package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class DirectionsFragment extends Fragment {

    @BindView(R.id.simple_exo_play_view)SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.direction)TextView mDirections;

    private static final String STEPS_LIST = "sList";
    private static final String POSITION = "position";

    private ArrayList<RecipeResponse.StepsBean> sList;
    private String videoUrl;
    private String directionString;
    private int position;
    private SimpleExoPlayer exoPlayer;
    private Unbinder unbinder;
    private boolean isFullScreen = false;

    public DirectionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DirectionsFragment newInstance(ArrayList<RecipeResponse.StepsBean> sList, int position) {
        DirectionsFragment fragment = new DirectionsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST,sList);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sList = getArguments().getParcelableArrayList(STEPS_LIST);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_directions, container, false);
        unbinder = ButterKnife.bind(this,view);

        view.findViewById(R.id.exo_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFullScreen){
                    isFullScreen = true;
                    mSimpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mDirections.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Fullscreen on",Toast.LENGTH_SHORT).show();
                } else {
                    isFullScreen = false;
                    mSimpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    mDirections.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Fullscreen off",Toast.LENGTH_SHORT).show();
                }
            }
        });


        videoUrl = sList.get(position).getVideoURL();
        directionString = sList.get(position).getDescription();

        mDirections.setText(directionString);

        initializePlayer(Uri.parse(videoUrl));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        unbinder.unbind();
    }

    private void initializePlayer(Uri mediaUri){
        if(exoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mSimpleExoPlayerView.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(getContext(),"KissTheBaker");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(),userAgent), new DefaultExtractorsFactory(),null,null);
            exoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer(){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }
}

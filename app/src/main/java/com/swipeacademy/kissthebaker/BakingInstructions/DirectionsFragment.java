package com.swipeacademy.kissthebaker.BakingInstructions;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DirectionsFragment extends Fragment {

    @Nullable @BindView(R.id.simple_exo_play_view)SimpleExoPlayerView mSimpleExoPlayerView;
    @Nullable @BindView(R.id.direction)TextView mDirections;
    @Nullable @BindView(R.id.direction_appBar)AppBarLayout appBarLayout;
    @Nullable @BindView(R.id.directions_toolbar)Toolbar toolbar;
    @Nullable @BindView(R.id.directions_toolbarText)TextView toolbarText;
    @Nullable @BindView(R.id.directions_toolbar_back_button)ImageButton backButton;
    @Nullable @BindView(R.id.fragment_direction_layout)LinearLayout layout;

    private static final String STEPS_LIST = "sList";
    private static final String POSITION = "position";
    private static final String IS_FULLSCREEN = "is_fullscreen";
    private static final String IS_NULL = "is_null";
    private static final String EXO_CURRENT_POSITION = "current_position";

    private ArrayList<RecipeResponse.StepsBean> sList;
    private String videoUrl;
    private String description;
    private String directionString;
    private int position;
    private long exo_current_position = 0;
    private SimpleExoPlayer exoPlayer;
    private Unbinder unbinder;
    private boolean isFullScreen = false;
    private boolean isNull;

    public DirectionsFragment() {
        // Required empty public constructor
    }

    public static DirectionsFragment newInstance(ArrayList<RecipeResponse.StepsBean> sList, int position) {

        DirectionsFragment fragment = new DirectionsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST,sList);
        args.putInt(POSITION, position);

        if(position == -1){
            args.putBoolean(IS_NULL,true);
        } else {
            args.putBoolean(IS_NULL,false);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        isNull = getArguments().getBoolean(IS_NULL);
        if(!isNull) {
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

        checkFullScreen();

        if(savedInstanceState == null){
            isFullScreen = false;
        } else {
            isFullScreen = savedInstanceState.getBoolean(IS_FULLSCREEN);
            exo_current_position = savedInstanceState.getLong(EXO_CURRENT_POSITION);
        }

        if(!isNull) {

            videoUrl = sList.get(position).getVideoURL();
            description = sList.get(position).getShortDescription();
            directionString = sList.get(position).getDescription();

            if(toolbarText != null) {
                toolbarText.setText(description);
                mDirections.setText(directionString);


                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().onBackPressed();
                    }
                });
            }

            view.findViewById(R.id.exo_full).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isFullScreen) {
                        isFullScreen = true;
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    } else {
                        isFullScreen = false;
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }
            });

            initializePlayer(Uri.parse(videoUrl));
        } else {
            layout.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FULLSCREEN,isFullScreen);
        outState.putLong(EXO_CURRENT_POSITION,exoPlayer.getCurrentPosition());
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
            if (exo_current_position != 0){
                exoPlayer.seekTo(exo_current_position);
            }
        }
    }

    private void releasePlayer(){

        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void checkFullScreen(){

    Configuration newConfig = new Configuration();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFullScreen = true;
            Toast.makeText(getContext(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            isFullScreen = false;
            Toast.makeText(getContext(), "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

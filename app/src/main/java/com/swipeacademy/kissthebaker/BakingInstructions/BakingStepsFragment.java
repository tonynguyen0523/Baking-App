package com.swipeacademy.kissthebaker.BakingInstructions;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BakingStepsFragment extends Fragment {

    static final String STEPS_LIST = "steps_list";

    @BindView(R.id.baking_steps_recyclerView)RecyclerView recyclerView;

    private ArrayList<RecipeResponse.StepsBean> stepsList;
    private Unbinder unbinder;

    public static BakingStepsFragment newInstance(ArrayList<RecipeResponse.StepsBean> stepsList){
        BakingStepsFragment fragment = new BakingStepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST, stepsList);
        fragment.setArguments(args);
        return fragment;
    }

    public BakingStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepsList = getArguments().getParcelableArrayList(STEPS_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_baking_steps, container, false);
        unbinder = ButterKnife.bind(this,view);

        BakingStepsRecyclerAdapter adapter = new BakingStepsRecyclerAdapter(getContext(), stepsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

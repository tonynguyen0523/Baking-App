package com.swipeacademy.kissthebaker.BakingInstructions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionsFragment extends Fragment {

    /**
     * Use to save and retrieve ingredients array list
     */
    private static final String INGREDIENTS_LIST = "iList";

    /**
     * Use to save and retrieve steps array list
     */
    private static final String STEPS_LIST = "sList";

    /**
     * Use to save and retrieve current scroll position
     */
    private static final String SCROLL_POSITION = "scroll_position";

    @BindView(R.id.ingredients_recyclerView)
    RecyclerView mIRecyclerView;
    @BindView(R.id.directions_recyclerView)
    RecyclerView mSRecyclerView;
    @BindView(R.id.instructions_fragment_layout)
    LinearLayout mLinearLayout;
    @BindView(R.id.instruction_scrollView)
    NestedScrollView mScrollView;

    private ArrayList<RecipeResponse.IngredientsBean> iList;
    private ArrayList<RecipeResponse.StepsBean> sList;
    private IngredientsRecyclerAdapter ingredientsRecyclerAdapter;
    private DirectionsRecyclerAdapter directionsRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;
    private Unbinder unbinder;
    private int scrollPosition;


    public static InstructionsFragment newInstance(
            ArrayList<RecipeResponse.IngredientsBean> iList, ArrayList<RecipeResponse.StepsBean> sList) {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_LIST, iList);
        args.putParcelableArrayList(STEPS_LIST, sList);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Callback for when a direction item is selected
     */
    public interface InstructionCallBack{
        void onDirectionSelected(ArrayList<RecipeResponse.StepsBean> sList,
                                 int position);
    }

    /**
     * Empty constructor
     */
    public InstructionsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iList = getArguments().getParcelableArrayList(INGREDIENTS_LIST);
        sList = getArguments().getParcelableArrayList(STEPS_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        unbinder = ButterKnife.bind(this, view);

        ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(getContext(), iList);
        mIRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIRecyclerView.setAdapter(ingredientsRecyclerAdapter);
        mIRecyclerView.setNestedScrollingEnabled(false);

        directionsRecyclerAdapter = new DirectionsRecyclerAdapter(getContext(), sList);
        mLayoutManager = new LinearLayoutManager(getContext());
        mSRecyclerView.setLayoutManager(mLayoutManager);
        mSRecyclerView.setAdapter(directionsRecyclerAdapter);
        mSRecyclerView.setNestedScrollingEnabled(false);

        directionsRecyclerAdapter.setOnInstructionClickListener(new DirectionsRecyclerAdapter.InstructionClickListener() {
            @Override
            public void onInstructionClicked(View view, int position) {
                ((InstructionCallBack)getActivity()).onDirectionSelected(sList,position);
            }
        });

        if(savedInstanceState != null){
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);

            // Scroll back to saved position
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.scrollTo(0, scrollPosition);
                }
            });
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_POSITION, mScrollView.getScrollY());
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollPosition = mScrollView.getScrollY();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

package com.swipeacademy.kissthebaker.BakingInstructions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swipeacademy.kissthebaker.Main.RecipeResponse;
import com.swipeacademy.kissthebaker.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BakingStepsFragment extends Fragment {

    static final String STEPS_LIST = "steps_list";
    static final String CURRENT_STEP = "current_step";

    @BindView(R.id.baking_steps_recyclerView)RecyclerView recyclerView;

//    @BindView(R.id.steps_tabs)TabLayout stepsTabs;
//    @BindView(R.id.steps_viewPager)ViewPager viewPager;

    private ArrayList<RecipeResponse.StepsBean> stepsList;
    private Unbinder unbinder;
//    private StepsPagerAdapter pagerAdapter;
//    private int currentStep;

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

//        pagerAdapter = new StepsPagerAdapter(getChildFragmentManager(),stepsList);
//        stepsTabs.setupWithViewPager(viewPager);
//        viewPager.setAdapter(pagerAdapter);

        InstructionsRecyclerAdapter adapter = new InstructionsRecyclerAdapter(getContext(), stepsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(CURRENT_STEP,viewPager.getCurrentItem());
    }

    private static class StepsPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<RecipeResponse.StepsBean> sList;

        public StepsPagerAdapter(FragmentManager fm, ArrayList<RecipeResponse.StepsBean>sList) {
            super(fm);
            this.sList = sList;
        }

        @Override
        public Fragment getItem(int position) {
//            BakingStepsFragment bakingStepsFragment = BakingStepsFragment.newInstance(sList);
//            switch (position){
//                case 0:
//                case 1:
//            }
            StepFragment stepFragment = StepFragment.newInstance(sList,position);
            return stepFragment;
        }

        @Override
        public int getCount() {
            return sList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position + 1);
        }
    }
}

package com.swipeacademy.kissthebaker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by tonyn on 7/28/2017.
 */

public class NonScrollingLayoutManager extends LinearLayoutManager {

    private boolean isScrollable = true;

    public NonScrollingLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollable = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollable && super.canScrollVertically();
    }
}

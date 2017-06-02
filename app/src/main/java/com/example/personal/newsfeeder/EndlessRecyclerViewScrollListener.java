package com.example.personal.newsfeeder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by personal on 6/1/2017.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 3;

    private int currentPage = 0 ;

    private int startingPageIndex = 1;

    private int previousItemCount = 0;

    private boolean loading = true;

    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager)
    {
        mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager)
    {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold*layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager)
    {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold*layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions)
    {
        int maxSize = 0;
        for(int i=0; i<lastVisibleItemPositions.length; i++)
        {
            if(i == 0)
            {
                maxSize = lastVisibleItemPositions[i];
            }
            else if(lastVisibleItemPositions[i] > maxSize)
            {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if(mLayoutManager instanceof StaggeredGridLayoutManager)
        {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager)mLayoutManager)
                    .findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        }
        else if(mLayoutManager instanceof GridLayoutManager)
        {
            lastVisibleItemPosition = ((GridLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        }
        else if(mLayoutManager instanceof LinearLayoutManager)
        {
            lastVisibleItemPosition = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        }

        if(totalItemCount < previousItemCount)
        {
            this.currentPage = this.startingPageIndex;
            previousItemCount = totalItemCount;
            if(totalItemCount == 0)
            {
                loading = true;
            }
        }

        if(loading && (totalItemCount > previousItemCount))
        {
            loading = false;
            previousItemCount = totalItemCount;
        }

        if(!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount)
        {
            currentPage++;
            onLoadMore(currentPage,totalItemCount,recyclerView);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView);

    public void resetState()
    {
        this.currentPage = this.startingPageIndex;
        this.previousItemCount = 0;
        this.loading = true;
    }




}

package com.example.personal.newsfeeder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by personal on 3/16/2017.
 */

public class ArticleLoader extends AsyncTaskLoader<List<TheArticle>> {

    private String mURL;

    public ArticleLoader(Context context,String url)
    {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    @Override
    public List<TheArticle> loadInBackground() {
        if(mURL == null)
        {
            return null;
        }
        List<TheArticle> articles = QueryUtils.fetchArticles(mURL);
        return articles;
    }
}

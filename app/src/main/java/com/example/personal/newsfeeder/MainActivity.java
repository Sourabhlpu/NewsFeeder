package com.example.personal.newsfeeder;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TheArticle>>,
RVAdapter.ListItemOnClickHandler{
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/" +
            "search";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView mEmptyTextView;

    private static final String API_KEY = "ce10d58e-3c68-451c-beb3-7b6a79f5b75f";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static boolean usingSearch = false;

    private EndlessRecyclerViewScrollListener scrollListener;
    private Uri.Builder uriBuilder;
    private String page = "1";

    public String createAPIQueryString(String currentPage)
    {
        Log.v(LOG_TAG,"The value of the page index " + currentPage);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_orderby_default));
        String pageSize = sharedPrefs.getString(getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default));

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        uriBuilder = baseUri.buildUpon();

        if(orderBy.equals("oldest"))
        {
            uriBuilder.appendQueryParameter("from-date","2017-01-01");
            uriBuilder.appendQueryParameter("use-date","published");

        }
        // if(orderBy.equals("relevance") )

        uriBuilder.appendQueryParameter("order-by",orderBy);
        uriBuilder.appendQueryParameter("show-fields","byline,thumbnail,trailText,body");
        uriBuilder.appendQueryParameter("page",currentPage);
        uriBuilder.appendQueryParameter("page-size",pageSize);
        uriBuilder.appendQueryParameter("api-key",API_KEY);

        return uriBuilder.toString();

    }

    @Override
    public Loader<List<TheArticle>> onCreateLoader(int id, Bundle args) {


       String queryString = createAPIQueryString(page);

        Log.v("The url",LOG_TAG + uriBuilder.toString());

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<TheArticle>> loader, List<TheArticle> articles) {

        findViewById(R.id.progress_bar).setVisibility(RecyclerView.GONE);
        if (articles != null && !articles.isEmpty()) {
            //articles.clear();

            mAdapter.updateDataset(articles, mRecyclerView);
            Log.v(LOG_TAG,"The articles object is " + articles);

        } else {
            mEmptyTextView.setText("No atricles found");
            findViewById(R.id.progress_bar).setVisibility(RecyclerView.GONE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<TheArticle>> loader) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info != null && info.isConnected())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }
        else
        {
            findViewById(R.id.progress_bar).setVisibility(RecyclerView.GONE);
            mEmptyTextView.setText("No internet connection");
        }



        mRecyclerView.setHasFixedSize(true);

        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);


        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);



        mAdapter = new RVAdapter(this, new ArrayList<TheArticle>(), this);
        mRecyclerView.setAdapter(mAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView) {

                loadNextDataFromApi(currentPage);

            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        scrollListener.resetState();


    }

    public void loadNextDataFromApi(int currentPage) {
        page = currentPage + "";
        //Log.v(LOG_TAG,"PAGE VALUE IN THE load.. function " + page);
        getLoaderManager().destroyLoader(EARTHQUAKE_LOADER_ID);
        getLoaderManager().restartLoader(EARTHQUAKE_LOADER_ID,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if(id == R.id.settings)
        {
           Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * this is the method that main activity overrides to implement onClick
    * of the ListItemOnClickHandler interface from the adapter.
    *  We use intent to open the detail activity and pass the details of the article with it
     */

    @Override
    public void onClick(TheArticle article) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context,destinationClass);

        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_avatar_name),
                article.getmAvatarName());
        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_avatar_sub),
                article.getmAvatarSub());
        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_image_url),
                article.getmImageURL());
        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_description),
                article.getmTheThreeLines());
        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_title),
                article.getmTheTitle());
        intentToStartDetailActivity.putExtra(getString(R.string.putExtra_name_initial_letter),
                article.getmAvatarInitial());
        intentToStartDetailActivity.putExtra(getString(R.string.detailActivityIntent),
                article.getmDetailPageLink());

        startActivity(intentToStartDetailActivity);
    }
}

package com.example.personal.newsfeeder;


import android.app.LoaderManager;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TheArticle>> {
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/" +
            "search";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyTextView;

    private static final String API_KEY = "ce10d58e-3c68-451c-beb3-7b6a79f5b75f";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static boolean usingSearch = false;

    @Override
    public Loader<List<TheArticle>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_orderby_default));
        String pageSize = sharedPrefs.getString(getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default));

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if(orderBy.equals("oldest"))
        {
            uriBuilder.appendQueryParameter("from-date","2017-01-01");
            uriBuilder.appendQueryParameter("use-date","published");

        }
       // if(orderBy.equals("relevance") )

        uriBuilder.appendQueryParameter("order-by",orderBy);
        uriBuilder.appendQueryParameter("show-fields","byline,thumbnail,trailText");
        uriBuilder.appendQueryParameter("page-size",pageSize);
        uriBuilder.appendQueryParameter("api-key",API_KEY);



        Log.v("The url",LOG_TAG + uriBuilder.toString());

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<TheArticle>> loader, List<TheArticle> articles) {

        findViewById(R.id.progress_bar).setVisibility(RecyclerView.GONE);
        if (articles != null && !articles.isEmpty()) {
            //articles.clear();

            mAdapter.updateDataset(articles);

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


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        // arraylist for storing the section objects

        List<Section> sections = new ArrayList<Section>();

        //creating the section class objects

        Section technology = new Section(R.drawable.placeholder,getString(R.string.technology_section));
        Section film = new Section(R.drawable.placeholder,getString(R.string.film_section));
        Section sports = new Section(R.drawable.placeholder,getString(R.string.sports_section));
        Section politics = new Section(R.drawable.placeholder,getString(R.string.politics_section));
        Section culture = new Section(R.drawable.placeholder,getString(R.string.culture_section));
        Section education = new Section(R.drawable.placeholder,getString(R.string.education_section));
        Section business = new Section(R.drawable.placeholder,getString(R.string.business_section));

        //adding the above objects to the arrylist

        sections.add(technology);
        sections.add(film);
        sections.add(sports);
        sections.add(politics);
        sections.add(culture);
        sections.add(education);
        sections.add(business);


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


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);


        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RVAdapter(this, new ArrayList<TheArticle>());
        mRecyclerView.setAdapter(mAdapter);

        //setting up the section view in the main view
        RecyclerView sectionRecyclerView = (RecyclerView)findViewById(R.id.section_recycler_view);
        sectionRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        sectionRecyclerView.setLayoutManager(layoutManager);

        SectionRVAdapter sectionAdapter = new SectionRVAdapter(this,sections);
        sectionRecyclerView.setAdapter(sectionAdapter);

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
}

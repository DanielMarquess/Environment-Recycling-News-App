package com.example.android.environmentrecyclingnewsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.environmentrecyclingnewsapp.utils.NetworkUtil;
import com.example.android.environmentrecyclingnewsapp.utils.QueryUtils;
import com.example.android.environmentrecyclingnewsapp.views.Article;
import com.example.android.environmentrecyclingnewsapp.views.ArticleAdapter;
import com.example.android.environmentrecyclingnewsapp.views.ArticleLoader;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {

    private static final int ARTICLE_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tryAgainBtn = (Button) findViewById(R.id.try_again_btn);
        tryAgainBtn.setVisibility(GONE);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        ListView articleListView = (ListView) findViewById(R.id.list);
        articleListView.setEmptyView(mEmptyStateTextView);


        if (NetworkUtil.isNetworkConnected(this)) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            View loadingSpinner = findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(GONE);
            tryAgainBtn.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_text_message);
        }

        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        articleListView.setAdapter(mAdapter);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Article currentArticle = mAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentArticle.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        return new ArticleLoader(this, QueryUtils.ARTICLE_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        View loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(GONE);
        mEmptyStateTextView.setText(R.string.no_articles_text_message);
        mAdapter.clear();

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}

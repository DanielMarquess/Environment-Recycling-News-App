package com.example.android.environmentrecyclingnewsapp.views;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.environmentrecyclingnewsapp.utils.QueryUtils;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private List<Article> mArticles;

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mArticles != null) {
            deliverResult(mArticles);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }

    @Override
    public void deliverResult(List<Article> articles) {
        mArticles = articles;
        super.deliverResult(mArticles);
    }
}

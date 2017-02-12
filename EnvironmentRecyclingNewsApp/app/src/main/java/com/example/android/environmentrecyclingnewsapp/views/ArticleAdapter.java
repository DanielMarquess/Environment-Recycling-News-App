package com.example.android.environmentrecyclingnewsapp.views;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.environmentrecyclingnewsapp.R;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static class ViewHolderItem {
        TextView articleTitle;
        TextView articleSection;
    }

    public ArticleAdapter(Activity context, List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.articleTitle = (TextView) listItemView.findViewById(R.id.article_title);
            viewHolder.articleSection = (TextView) listItemView.findViewById(R.id.article_section);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) listItemView.getTag();
        }

        Article currentArticle = getItem(position);

        viewHolder.articleTitle.setText(currentArticle.getTitle());
        viewHolder.articleSection.setText(currentArticle.getSection());

        return listItemView;
    }
}
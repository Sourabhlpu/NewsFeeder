package com.example.personal.newsfeeder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by personal on 3/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ArticleViewHolder> {

    private static final String LOG_TAG = RVAdapter.class.getSimpleName();

    List<TheArticle> mArticles;
    Context mContext;
    int mRowIndex;

    public RVAdapter(Context context, List<TheArticle> articles) {

        mArticles = articles;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mRowIndex++;

        //Log.v(LOG_TAG, "The rowIndex is " + mRowIndex);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        ArticleViewHolder avh = new ArticleViewHolder(v);
        return avh;

    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        Log.v(LOG_TAG, "The position variable is " + position);
        Log.v(LOG_TAG, "The rowIndex is " + mRowIndex);
        holder.mAvatarView.setText(mArticles.get(position).getmAvatarInitial());
        holder.mAvatarNameView.setText(mArticles.get(position).getmAvatarName());
        holder.mAvatarSubView.setText(mArticles.get(position).getmAvatarSub());

        if (mRowIndex == 1) {

            holder.horizontalList.setLayoutManager(new LinearLayoutManager(holder.context, LinearLayoutManager.HORIZONTAL, false));
            holder.horizontalAdapter = new SectionRVAdapter(holder.context,holder.sections);
            holder.horizontalList.setAdapter(holder.horizontalAdapter);
            holder.horizontalAdapter.setDataset(holder.sections);

        }

        String imageUrl = mArticles.get(position).getmImageURL();

        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_img)
                .into(holder.mImageView);
        holder.mTheTitleView.setText(mArticles.get(position).getmTheTitle());
        // holder.mTheSubtitleView.setText(mArticles.get(position).getmTheSubtitle());
        holder.mTheThreeLinesView.setText(mArticles.get(position).getmTheThreeLines());
        //holder.mBookmarkView.setImageResource(mArticles.get(position).getmBookmarkResourceId());
        // holder.mHeartView.setImageResource(mArticles.get(position).getmHeartResourceId());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateDataset(List<TheArticle> articles) {
        mArticles = articles;
        this.notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView mAvatarView;
        TextView mAvatarNameView;
        TextView mAvatarSubView;
        ImageView mImageView;
        TextView mTheTitleView;
        TextView mTheSubtitleView;
        TextView mTheThreeLinesView;
        ImageView mBookmarkView;
        ImageView mHeartView;
        RecyclerView horizontalList;
        Context context;
        ArrayList<Section> sections;
        SectionRVAdapter horizontalAdapter;
        int rowIndex;

        ArticleViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            mAvatarView = (TextView) itemView.findViewById(R.id.avatar_image);
            mAvatarNameView = (TextView) itemView.findViewById(R.id.avatar_name);
            mAvatarSubView = (TextView) itemView.findViewById(R.id.avatar_subhead);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mTheTitleView = (TextView) itemView.findViewById(R.id.the_title);
            mTheThreeLinesView = (TextView) itemView.findViewById(R.id.the_three_lines);
            mBookmarkView = (ImageView) itemView.findViewById(R.id.bookmark_image);
            mHeartView = (ImageView) itemView.findViewById(R.id.heart_image);
            sections = new ArrayList<Section>();


            //creating the section class objects

            Section technology = new Section(R.drawable.placeholder, "Technology");
            Section film = new Section(R.drawable.placeholder, "Films");
            Section sports = new Section(R.drawable.placeholder, "Sports");
            Section politics = new Section(R.drawable.placeholder, "Politics");
            Section culture = new Section(R.drawable.placeholder, "Culture");
            Section education = new Section(R.drawable.placeholder, "Education");
            Section business = new Section(R.drawable.placeholder, "Business");

            //adding the above objects to the arrylist

            sections.add(technology);
            sections.add(film);
            sections.add(sports);
            sections.add(politics);
            sections.add(culture);
            sections.add(education);
            sections.add(business);

            horizontalList = (RecyclerView) itemView.findViewById(R.id.section_recycler_view);


        }
    }
}

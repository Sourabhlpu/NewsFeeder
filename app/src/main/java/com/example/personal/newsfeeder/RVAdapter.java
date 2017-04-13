package com.example.personal.newsfeeder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by personal on 3/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ArticleViewHolder> {

    List<TheArticle> mArticles;
    Context mcontext;

    public RVAdapter(Context context,List<TheArticle> articles)
    {

        mArticles = articles;
        mcontext = context;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder
    {
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

        ArticleViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            mAvatarView = (TextView)itemView.findViewById(R.id.avatar_image);
            mAvatarNameView = (TextView)itemView.findViewById(R.id.avatar_name);
            mAvatarSubView = (TextView)itemView.findViewById(R.id.avatar_subhead);
            mImageView = (ImageView)itemView.findViewById(R.id.image_view);
            mTheTitleView = (TextView)itemView.findViewById(R.id.the_title);

            mTheThreeLinesView = (TextView)itemView.findViewById(R.id.the_three_lines);
            mBookmarkView = (ImageView)itemView.findViewById(R.id.bookmark_image);
            mHeartView = (ImageView)itemView.findViewById(R.id.heart_image);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        ArticleViewHolder avh = new ArticleViewHolder(v);
        return  avh;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.mAvatarView.setText(mArticles.get(position).getmAvatarInitial());
        holder.mAvatarNameView.setText(mArticles.get(position).getmAvatarName());
        holder.mAvatarSubView.setText(mArticles.get(position).getmAvatarSub());

        String imageUrl = mArticles.get(position).getmImageURL();

        Picasso.with(mcontext)
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

    public void updateDataset(List<TheArticle> articles)
    {
        mArticles = articles;
        this.notifyDataSetChanged();
    }
}

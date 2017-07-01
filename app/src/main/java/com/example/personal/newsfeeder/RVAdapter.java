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

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = RVAdapter.class.getSimpleName();

    private final int CARD_WITH_HORIZONTAL_SCROLL = 1;
    private final int CARD_WITHOUT_HORIZONTAL_SCROLL = 2;


   /*
    * the interface that is defined below handles the clicks on the articles.
    * In the constructor we set this variable equal to the MainActivity that implements the
    * on click of this interface.
    */
    final private ListItemOnClickHandler mOnClickHandler;

    List<TheArticle> mArticles;
    Context mContext;
    int mRowIndex;

    /*
     * Declaring the interface that is used to handle the on click
     * on individual articles.

     */

    public interface ListItemOnClickHandler {

        void onClick(TheArticle article);
    }



    public RVAdapter(Context context, List<TheArticle> articles, ListItemOnClickHandler onClickHandler) {

        mArticles = articles;
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0)
        {
            return CARD_WITH_HORIZONTAL_SCROLL;
        }
        else{
            return CARD_WITHOUT_HORIZONTAL_SCROLL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

    switch (viewType)
    {
        case CARD_WITH_HORIZONTAL_SCROLL:
            View v1 = inflater.inflate(R.layout.card_item_with_horizontal_scroll, parent, false);
            viewHolder = new ArticleViewHolderWithHorizontalScroll(v1);
            break;
        case CARD_WITHOUT_HORIZONTAL_SCROLL:
            View v2 = inflater.inflate(R.layout.card_item,parent,false);
            viewHolder = new ArticleViewHolder(v2);
            break;
        default:
            View v3 = inflater.inflate(R.layout.card_item,parent,false);
            viewHolder = new ArticleViewHolder(v3);
            break;

    }
    return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType())
        {
            case CARD_WITH_HORIZONTAL_SCROLL:
                ArticleViewHolderWithHorizontalScroll vh1 = (ArticleViewHolderWithHorizontalScroll) holder;
                configureArticleViewHolderWithHorizontalScroll(vh1,position);
                break;
            case CARD_WITHOUT_HORIZONTAL_SCROLL:
                ArticleViewHolder vh2 = (ArticleViewHolder)holder;
                configureArticleViewHolder(vh2,position);
        }


    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateDataset(final List<TheArticle> articles, RecyclerView view) {

        if(mArticles.isEmpty())
        {
            mArticles = articles;
            this.notifyDataSetChanged();
        }
        else
        {
            final int currentSize = this.getItemCount();
            view.post(new Runnable() {

                @Override
                public void run() {

                    //Log.v(LOG_TAG,"THE SIZE of the items in the list " + currentSize);
                    //mArticles.clear();
                    mArticles.addAll(articles);
                    Log.v(LOG_TAG,"The articles after the addAll func" + mArticles.toString());
                    RVAdapter.this.notifyItemRangeInserted(currentSize,articles.size()-1);
                }
            });

        }



    }

    public  class ArticleViewHolderWithHorizontalScroll extends RecyclerView.ViewHolder implements
    View.OnClickListener{
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

        ArticleViewHolderWithHorizontalScroll(View itemView) {
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


            itemView.setOnClickListener(this);


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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(mArticles.get(position));

        }
    }

    public  class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        Context context;


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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(mArticles.get(position));
        }
    }

    private void configureArticleViewHolderWithHorizontalScroll(ArticleViewHolderWithHorizontalScroll holder, int position)
    {

        holder.mAvatarView.setText(mArticles.get(position).getmAvatarInitial());
        holder.mAvatarNameView.setText(mArticles.get(position).getmAvatarName());
        holder.mAvatarSubView.setText(mArticles.get(position).getmAvatarSub());



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


        holder.horizontalList.setLayoutManager(new LinearLayoutManager(holder.context, LinearLayoutManager.HORIZONTAL, false));
        holder.horizontalAdapter = new SectionRVAdapter(holder.context,holder.sections);
        holder.horizontalList.setAdapter(holder.horizontalAdapter);
        holder.horizontalAdapter.setDataset(holder.sections);

    }

    public void configureArticleViewHolder(ArticleViewHolder holder, int position)
    {
        //Log.v(LOG_TAG, "The position variable is " + position);
        //Log.v(LOG_TAG, "The rowIndex is " + mRowIndex);
        holder.mAvatarView.setText(mArticles.get(position).getmAvatarInitial());
        holder.mAvatarNameView.setText(mArticles.get(position).getmAvatarName());
        holder.mAvatarSubView.setText(mArticles.get(position).getmAvatarSub());


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
}

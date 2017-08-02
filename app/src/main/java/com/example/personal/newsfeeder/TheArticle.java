package com.example.personal.newsfeeder;

/**
 * Created by personal on 3/12/2017.
 */

public class TheArticle {

    private String mAvatarInitial;
    private String mAvatarName;
    private String mDate;
    private String mImageURL = "https://goo.gl/PHbk71";
    private String mTheTitle;

    private String mTheThreeLines;
    private int mBookmarkResourceId;
    private int mHeartResourceId;
    private String mDetailPageLink;

    public TheArticle(String avatarName, String date, String imageURL,
                      String theTitle, String theThreeLines, int bookmarkResourceId,
                      int heartResourceId, String detailPageLink) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mDate = date;
        mImageURL = imageURL;
        mTheTitle = theTitle;

        mTheThreeLines = theThreeLines;
        mBookmarkResourceId = bookmarkResourceId;
        mHeartResourceId = heartResourceId;
        mDetailPageLink = detailPageLink;
    }

    public TheArticle(String avatarName, String date, String imageURL,
                      String theTitle, String theThreeLines, String detailPageLink
    ) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mDate = date;
        mImageURL = imageURL;
        mTheTitle = theTitle;
        mDetailPageLink = detailPageLink;
        mTheThreeLines = theThreeLines;

    }


    public String getmAvatarInitial() {
        return mAvatarInitial;
    }

    public String getmAvatarName() {
        return mAvatarName;
    }

    public String getmDate() {
        return mDate;

    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmTheTitle() {
        return mTheTitle;
    }

    public String getmTheThreeLines() {
        return mTheThreeLines;
    }

    public String getmDetailPageLink(){return mDetailPageLink; }

    public int getmBookmarkResourceId() {
        return mBookmarkResourceId;
    }

    public int getmHeartResourceId() {
        return mHeartResourceId;
    }

}

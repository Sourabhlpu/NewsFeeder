package com.example.personal.newsfeeder;

/**
 * Created by personal on 3/12/2017.
 */

public class TheArticle {

    private String mAvatarInitial;
    private String mAvatarName;
    private String mAvatarSub;
    private String mImageURL = "https://goo.gl/PHbk71";
    private String mTheTitle;

    private String mTheThreeLines;
    private int mBookmarkResourceId;
    private int mHeartResourceId;

    public TheArticle(String avatarName, String avatarSub, String imageURL,
                      String theTitle, String theThreeLines, int bookmarkResourceId,
                      int heartResourceId) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mAvatarSub = avatarSub;
        mImageURL = imageURL;
        mTheTitle = theTitle;

        mTheThreeLines = theThreeLines;
        mBookmarkResourceId = bookmarkResourceId;
        mHeartResourceId = heartResourceId;
    }

    public TheArticle(String avatarName, String avatarSub, String imageURL,
                      String theTitle, String theThreeLines
    ) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mAvatarSub = avatarSub;
        mImageURL = imageURL;
        mTheTitle = theTitle;

        mTheThreeLines = theThreeLines;

    }


    public String getmAvatarInitial() {
        return mAvatarInitial;
    }

    public String getmAvatarName() {
        return mAvatarName;
    }

    public String getmAvatarSub() {
        return mAvatarSub;

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

    public int getmBookmarkResourceId() {
        return mBookmarkResourceId;
    }

    public int getmHeartResourceId() {
        return mHeartResourceId;
    }

}

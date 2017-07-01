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
    private String mBodyText;

    public TheArticle(String avatarName, String avatarSub, String imageURL,
                      String theTitle, String theThreeLines, int bookmarkResourceId,
                      int heartResourceId, String bodyText) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mAvatarSub = avatarSub;
        mImageURL = imageURL;
        mTheTitle = theTitle;

        mTheThreeLines = theThreeLines;
        mBookmarkResourceId = bookmarkResourceId;
        mHeartResourceId = heartResourceId;
        mBodyText = bodyText;
    }

    public TheArticle(String avatarName, String avatarSub, String imageURL,
                      String theTitle, String theThreeLines, String bodyText
    ) {
        mAvatarInitial = avatarName.charAt(0) + "";
        mAvatarName = avatarName;
        mAvatarSub = avatarSub;
        mImageURL = imageURL;
        mTheTitle = theTitle;
        mBodyText = bodyText;
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

    public String getmBodyText(){return mBodyText; }

    public int getmBookmarkResourceId() {
        return mBookmarkResourceId;
    }

    public int getmHeartResourceId() {
        return mHeartResourceId;
    }

}

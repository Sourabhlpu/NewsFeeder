package com.example.personal.newsfeeder.data.NewsContract;

import android.provider.BaseColumns;

/**
 * Created by personal on 8/2/2017.
 */

public class NewsContract {

    public static final class NewsEntry implements BaseColumns
    {
        public static final String  TABLE_NAME = "news";

        public static final String COLUMN_DATE = "date";

        public static final String COLUMN_NAME = "name";

        public static final String IMAGE_URL = "image url";

        public static final String TITLE = "title";

        public static final String ShortDescription = "description";
    }
}

package com.example.personal.newsfeeder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView avatarNameTv, avatarSubTv, descriptionTv, titleTv, avatarImage;
    private ImageView imageIv;
    private String mImageUrl, mUserNamesFirstLetter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         avatarNameTv = (TextView)findViewById(R.id.avatar_name);
         avatarSubTv = (TextView)findViewById(R.id.avatar_subhead);
         descriptionTv = (TextView)findViewById(R.id.detail_activity_description);
         imageIv = (ImageView)findViewById(R.id.detail_activity_image);
         titleTv = (TextView)findViewById(R.id.title_text_view);
         avatarImage = (TextView)findViewById(R.id.avatar_image);


        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null)
        {
            if(intentThatStartedThisActivity.hasExtra(getString(R.string.putExtra_avatar_name))     &&
                    intentThatStartedThisActivity.hasExtra(getString(R.string.putExtra_avatar_sub)) &&
                    intentThatStartedThisActivity.hasExtra(getString(R.string.putExtra_description))&&
                    intentThatStartedThisActivity.hasExtra(getString(R.string.putExtra_image_url)))
            {
                avatarNameTv.setText(intentThatStartedThisActivity.
                        getStringExtra(getString(R.string.putExtra_avatar_name)));
                avatarNameTv.setTextColor(ContextCompat.getColor(this,R.color.white));

                avatarSubTv.setText(intentThatStartedThisActivity.
                getStringExtra(getString(R.string.putExtra_avatar_sub)));
                avatarSubTv.setTextColor(ContextCompat.getColor(this,R.color.white));

                avatarImage.setText(intentThatStartedThisActivity.
                getStringExtra(getString(R.string.putExtra_name_initial_letter)));

                /*
                 * We retrieve the detailed article body for an article from the server.
                 * In order to properly use spacing and paragraphs we choose to use "Body" instead
                 * of "bodyText"
                 * "body" contained text in HTML. To solve that problem I used the below code
                 *  used --> https://stackoverflow.com/questions/2918920/decode-html-entities-in-android
                 *  to get help.
                 *
                 */
                String body;
                if(Build.VERSION.SDK_INT >= 24)
                {
                    body = Html.fromHtml(intentThatStartedThisActivity.
                            getStringExtra(getString(R.string.putExtra_body_text)),
                            Html.FROM_HTML_MODE_LEGACY).toString();
                }
                else
                {
                    body = Html.fromHtml(intentThatStartedThisActivity.
                            getStringExtra(getString(R.string.putExtra_body_text))).toString();
                }

                descriptionTv.setText(body);

                titleTv.setText(intentThatStartedThisActivity.
                getStringExtra(getString(R.string.putExtra_title)));

                mImageUrl = intentThatStartedThisActivity.getStringExtra(getString(R.string.putExtra_image_url));

                Picasso.with(this)
                        .load(mImageUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_img)
                        .into(imageIv);

            }
        }

            /*
             * using the app Toolbar view.
             * App Toolbar makes it easy to customise the action bar.
             * Here we set the Toolbar to act as action bar
             */

        //find the toolbar inside the activity_detail
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_detail);

        if(toolbar != null)
        {
            //set the toolbar to act as action bar for this activity
            setSupportActionBar(toolbar);
        }

        //setting the back button on the action bar
        // see this for reference https://stackoverflow.com/questions/26651602/display-back-arrow-on-toolbar-android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }
}

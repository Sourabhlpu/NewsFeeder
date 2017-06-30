package com.example.personal.newsfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView avatarNameTv, avatarSubTv, descriptionTv;
    private ImageView imageIv;
    private String mImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         avatarNameTv = (TextView)findViewById(R.id.detail_activity_avatar_name);
         avatarSubTv = (TextView)findViewById(R.id.detail_activity_avatar_sub);
         descriptionTv = (TextView)findViewById(R.id.detail_activity_description);
         imageIv = (ImageView)findViewById(R.id.detail_activity_image);

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

                avatarSubTv.setText(intentThatStartedThisActivity.
                getStringExtra(getString(R.string.putExtra_avatar_sub)));

                descriptionTv.setText(intentThatStartedThisActivity.
                getStringExtra(getString(R.string.putExtra_description)));

                mImageUrl = intentThatStartedThisActivity.getStringExtra(getString(R.string.putExtra_image_url));

                Picasso.with(this)
                        .load(mImageUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_img)
                        .into(imageIv);
            }
        }
    }
}

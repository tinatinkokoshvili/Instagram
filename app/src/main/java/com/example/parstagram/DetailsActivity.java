package com.example.parstagram;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";

    Post post;

    private TextView tvDetailsDescription;
    private TextView tvDetailsUsername;
    private TextView tvTimeAgo;
    private ImageView ivDetailsImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvDetailsDescription = findViewById(R.id.tvDetailsDescription);
        tvDetailsUsername = findViewById(R.id.tvDetailsUsername);
        tvTimeAgo = findViewById(R.id.tvTimeAgo);
        ivDetailsImage = findViewById(R.id.ivDetailsImage);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        Log.i(TAG, post.getDescription().toString());
        tvDetailsDescription.setText(post.getDescription());
        tvDetailsUsername.setText(post.getUser().getUsername());
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeAgo.setText(timeAgo);

        //If image is app is in landscape mode, use backdrop image path
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivDetailsImage);
        }
    }





}

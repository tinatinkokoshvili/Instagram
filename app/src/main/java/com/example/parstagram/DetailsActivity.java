package com.example.parstagram;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
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

    IgPost post;
    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvCreatedAt;
    private TextView tvUsernameBottom;
    private TextView tvViewComments;
    private ImageView ivHeart;
    private ImageView ivFilledHeart;
    private TextView tvLikes;
    private ImageView ivComment;

//    private TextView tvDetailsDescription;
//    private TextView tvDetailsUsername;
//    private TextView tvTimeAgo;
//    private ImageView ivDetailsImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvUsernameBottom = findViewById(R.id.tvUsernameBottom);
        tvViewComments = findViewById(R.id.tvViewComments);
        ivHeart = findViewById(R.id.ivHeart);
        ivFilledHeart = findViewById(R.id.ivFilledHeart);
        tvLikes = findViewById(R.id.tvLikes);
        ivComment = findViewById(R.id.ivComment);

        post = (IgPost) Parcels.unwrap(getIntent().getParcelableExtra(IgPost.class.getSimpleName()));

        // Bind the post data to the view elements
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvUsernameBottom.setText(post.getUser().getUsername());
        tvViewComments.setText("View all comments");
        Glide.with(this).load(R.drawable.ufi_heart).into(ivHeart);
        ivFilledHeart.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.ufi_heart_active).into(ivFilledHeart);
        Glide.with(this).load(R.drawable.ufi_comment).into(ivComment);
        tvLikes.setText(post.getLikes() + " Likes");
        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("heart", "Heart Clicked");
                //ivHeart.setVisibility(View.GONE);
                ivFilledHeart.setVisibility(View.VISIBLE);
                post.incrementLikes();
                tvLikes.setText(post.getLikes() + " Likes");
            }
        });
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
        Date createdAt = post.getCreatedAt();
        String timeAgo = IgPost.calculateTimeAgo(createdAt);
        tvCreatedAt.setText(timeAgo);

//        tvDetailsDescription = findViewById(R.id.tvDetailsDescription);
//        tvDetailsUsername = findViewById(R.id.tvDetailsUsername);
//        tvTimeAgo = findViewById(R.id.tvTimeAgo);
//        ivDetailsImage = findViewById(R.id.ivDetailsImage);
//

//
//        Log.i(TAG, post.getDescription().toString());
//        tvDetailsDescription.setText(post.getDescription());
//        tvDetailsUsername.setText(post.getUser().getUsername());
//        Date createdAt = post.getCreatedAt();
//        String timeAgo = IgPost.calculateTimeAgo(createdAt);
//        tvTimeAgo.setText(timeAgo);
//
//        //If image is app is in landscape mode, use backdrop image path
//        ParseFile image = post.getImage();
//        if (image != null) {
//            Glide.with(this).load(image.getUrl()).into(ivDetailsImage);
//        }
    }





}

package com.example.parstagram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "FeedActivity";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
          //      fetchTimelineAsync(0);
            }
        });

        rvPosts = findViewById(R.id.rvPosts);

        // initialize the array that will hold posts and create a PostsAdapter
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);

        // set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        // query posts from Parstagram
        //queryPosts();
    }

//    public void fetchTimelineAsync(int page) {
//        // specify what type of data we want to query - Post.class
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        // include data referred by user key
//        query.include(Post.KEY_USER);
//        // limit query to latest 20 items
//        query.setLimit(2);
//        // order posts by creation date (newest first)
//        query.addDescendingOrder("createdAt");
//        // start an asynchronous call for posts
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> posts, ParseException e) {
//                // check for errors
//                if (e != null) {
//                    Log.e(TAG, "Issue with getting posts", e);
//                    return;
//                }
//                adapter.clear();
//                // for debugging purposes let's print every post description to logcat
//                for (Post post : posts) {
//                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
//                }
//
//                // save received posts to list and notify adapter of new data
//                allPosts.addAll(posts);
//                swipeContainer.setRefreshing(false);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    private void queryPosts() {
//        // specify what type of data we want to query - Post.class
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        // include data referred by user key
//        query.include(Post.KEY_USER);
//        // limit query to latest 20 items
//        query.setLimit(2);
//        // order posts by creation date (newest first)
//        query.addDescendingOrder("createdAt");
//        // start an asynchronous call for posts
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> posts, ParseException e) {
//                // check for errors
//                if (e != null) {
//                    Log.e(TAG, "Issue with getting posts", e);
//                    return;
//                }
//
//                // for debugging purposes let's print every post description to logcat
//                for (Post post : posts) {
//                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
//                }
//
//                // save received posts to list and notify adapter of new data
//                allPosts.addAll(posts);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

}

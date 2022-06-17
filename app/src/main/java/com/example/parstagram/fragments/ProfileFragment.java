package com.example.parstagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.parstagram.EndlessRecyclerViewScrollListener;
import com.example.parstagram.IgPost;
import com.example.parstagram.LoginActivity;
import com.example.parstagram.ProfileAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment {

    private static final String TAG = "ProfileFragment";
    private Button btnLogout;
    private TextView tvUser;
    private ImageView ivProfile;
    private RecyclerView rvPosts;
    private TextView tvNumPosts;
    ProfileAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUser = view.findViewById(R.id.tvCurrentUser);
        ivProfile = view.findViewById(R.id.ivCurrentProfile);
        tvNumPosts = view.findViewById(R.id.tvNumPosts);

        tvUser.setText(ParseUser.getCurrentUser().getUsername());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
        rvPosts = (RecyclerView) view.findViewById(R.id.rvUserPosts);
        allPosts = new ArrayList<>();
        postAdapter = new ProfileAdapter(allPosts);

        queryPosts();


        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setAdapter(postAdapter);
    }

    public void onLogout() {
        Log.i(TAG, "onClick logout button");
        ParseUser.logOutInBackground();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    @Override
    protected void queryPosts() {
        //super.queryPosts();

        ParseQuery<IgPost> query = ParseQuery.getQuery(IgPost.class);
        query.include(IgPost.KEY_USER);
        Log.i(TAG, IgPost.KEY_USER + " "+ ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo(IgPost.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<IgPost>() {
            @Override
            public void done(List<IgPost> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Getting posts failed", e);
                    return;
                }
                postAdapter.clear();
                allPosts.addAll(posts);
                postAdapter.notifyDataSetChanged();
                tvNumPosts.setText(allPosts.size() + " Posts");

            }
        });
    }
}

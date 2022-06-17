package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    List<IgPost> posts;
    Context context;


    //pass posts array to constructor
    public ProfileAdapter(List<IgPost> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_user_post, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        IgPost post = posts.get(i);
        Glide.with(context).load(post.getImage().getUrl()).into(viewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    //create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivProfilePost);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Open details page when clicked
            int position = getAdapterPosition();
            IgPost post = posts.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(IgPost.class.getSimpleName(), Parcels.wrap(post));
            context.startActivity(intent);
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
}

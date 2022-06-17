package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.DetailsActivity;
import com.example.parstagram.R;
import com.example.parstagram.IgPost;
import com.parse.ParseFile;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<IgPost> posts;

    public PostsAdapter(Context context, List<IgPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IgPost post = posts.get(position);
        holder.bind(post);
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<IgPost> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        private ImageView ivMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvUsernameBottom = itemView.findViewById(R.id.tvUsernameBottom);
            tvViewComments = itemView.findViewById(R.id.tvViewComments);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivFilledHeart = itemView.findViewById(R.id.ivFilledHeart);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            itemView.setOnClickListener(this);
        }

        public void bind(IgPost post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsernameBottom.setText(post.getUser().getUsername());
            tvViewComments.setText("View all comments");
            Glide.with(context).load(R.drawable.ufi_heart).into(ivHeart);
            ivFilledHeart.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ufi_heart_active).into(ivFilledHeart);
            Glide.with(context).load(R.drawable.ufi_comment).into(ivComment);
            Glide.with(context).load(R.drawable.direct).into(ivMessage);
            tvLikes.setText(post.getLikes() + " Likes");
            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("heart", "Heart Clicked");
                    ivFilledHeart.setVisibility(View.VISIBLE);
                    //ivHeart.setVisibility(View.GONE);
                    //increment likes in database
                    post.incrementLikes();
                    post.saveInBackground();
                    tvLikes.setText(post.getLikes() + " Likes");
                }
            });
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            Date createdAt = post.getCreatedAt();
            String timeAgo = IgPost.calculateTimeAgo(createdAt);
            tvCreatedAt.setText(timeAgo);
        }

        @Override
        public void onClick(View v) {
            Log.i("Adapter", "post clicked");
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                IgPost post = posts.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(IgPost.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }
}

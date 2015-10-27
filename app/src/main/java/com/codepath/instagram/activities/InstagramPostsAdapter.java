package com.codepath.instagram.activities;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by trit on 10/26/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.InstagramPostsViewHolder> {
    private ArrayList<InstagramPost> posts;
    Context context;

    public InstagramPostsAdapter(ArrayList<InstagramPost> posts) {
        this.posts = posts;
    }

    @Override
    public InstagramPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_post, parent, false);

        InstagramPostsViewHolder viewHolder = new InstagramPostsViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InstagramPostsViewHolder holder, int position) {
        InstagramPost post = posts.get(position);

        holder.tvUserName.setText(post.user.userName);

        ForegroundColorSpan blueForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.blue_text));
        TypefaceSpan serifMediumTypeFaceSpan = new TypefaceSpan("sans-serif-medium");
        SpannableStringBuilder ssb = new SpannableStringBuilder(post.user.userName);
        ssb.setSpan(blueForegroundColorSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(serifMediumTypeFaceSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" ");
        if (post.caption != null) {
            ssb.append(post.caption);
            ForegroundColorSpan grayForeGroundColorSpan = new ForegroundColorSpan(
                    context.getResources().getColor(R.color.gray_text));
            TypefaceSpan serifTypeFaceSpan = new TypefaceSpan("sans-serif");
            ssb.setSpan(grayForeGroundColorSpan, ssb.length() - post.caption.length(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(serifTypeFaceSpan, ssb.length() - post.caption.length(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.tvCaption.setText(ssb, TextView.BufferType.NORMAL);
        holder.tvCreatedTime.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));
        holder.tvLikesCount.setText(Utils.formatNumberForDisplay(post.likesCount) + " likes");
        holder.ivAvatar.setImageURI(Uri.parse(post.user.profilePictureUrl));
        holder.ivImage.setImageURI(Uri.parse(post.image.imageUrl));
        holder.ivImage.setAspectRatio((float) post.image.imageWidth / post.image.imageHeight);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class InstagramPostsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public TextView tvCaption;
        public SimpleDraweeView ivAvatar;
        public SimpleDraweeView ivImage;
        public TextView tvLikesCount;
        public TextView tvCreatedTime;


        public InstagramPostsViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvLikesCount = (TextView) itemView.findViewById(R.id.tvLikesCount);
            tvCreatedTime = (TextView) itemView.findViewById(R.id.tvCreatedTime);
            ivAvatar = (SimpleDraweeView) itemView.findViewById(R.id.ivAvatar);
            ivImage = (SimpleDraweeView) itemView.findViewById(R.id.ivImage);
        }
    }
}

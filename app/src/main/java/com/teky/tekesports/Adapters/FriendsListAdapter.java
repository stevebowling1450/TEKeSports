package com.teky.tekesports.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teky.tekesports.Model.User;
import com.teky.tekesports.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lennyhicks on 12/9/16.
 * toornament-android.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<com.teky.tekesports.Adapters.FriendsListAdapter.FriendHolder> {

    public final List<User> friendList;
    private final Context context;

    public FriendsListAdapter(List<User> friendList, Context context) {
        this.friendList = friendList;
        this.context = context;
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);

        return new FriendHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        if (position < friendList.size()) {
            User mess = friendList.get(position);
            holder.bindGame(mess);

        }
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class FriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;
        @Nullable
        @Bind(R.id.friend_online)
        ImageView isOnline;

        @Nullable
        @Bind(R.id.friend_name)
        TextView friendName;

        @Bind(R.id.friends_list_button)
        ImageButton friendListButton;


        public FriendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        // Adds the data to the view.
        public void bindGame(User user) {
            this.user = user;
            if (isOnline != null) {
                if (user.getOnline()) {
                    isOnline.setImageDrawable(getIcon(MaterialDrawableBuilder.IconValue.ACCOUNT_CIRCLE));
                } else {
                    isOnline.setImageDrawable(getIcon(MaterialDrawableBuilder.IconValue.LED_OFF));
                }
            }
            if (friendName != null)
                friendName.setText(user.getUsername());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Tapped " + user.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    public Drawable getIcon(MaterialDrawableBuilder.IconValue icon) {
        // The method returns a MaterialDrawable, but as it is private to the builder you'll have to store it as a regular Drawable ;)

        return MaterialDrawableBuilder.with(context) // provide a context
                .setIcon(icon) // provide an icon
                .setColor(Color.GREEN) // set the icon color
                .setToActionbarSize() // set the icon size
                .build(); // Finally call build


    }
}

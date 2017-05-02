package com.teky.tekesports.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teky.tekesports.Model.Message;
import com.teky.tekesports.Model.User;
import com.teky.tekesports.R;
import com.teky.tekesports.Utils.FireBase;
import com.teky.tekesports.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.teky.tekesports.Utils.CurrentUser.currUser;

/**
 * Created by lennyhicks on 12/8/16.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    public List<Message> message;
    private Context context;

    public ChatAdapter(List<Message> message, Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);

        return new ChatAdapter.ChatHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatHolder holder, int position) {
        if (position < message.size()){
            Message mess = message.get(position);
            if ( mess.getMessage() != null) {
                holder.bindGame(mess);
            }

        }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Nullable @Bind(R.id.message_text)
        TextView messageText;

        @Nullable @Bind(R.id.message_date)
        TextView messageDate;

        private Message message;
        private User sender;


        public ChatHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        // Adds the data to the view.
        public void bindGame(Message message){
            this.message = message;
            try {
                messageText.setText(Utils.formatChat(message));
                messageDate.setText(message.getSendDate());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Tapped " +message.getMessageId(), Toast.LENGTH_SHORT).show();

            ArrayList<User> friends = currUser.getFriends();
            if (friends == null){
                friends = new ArrayList<>();
            }

//            FirebaseDatabase.getInstance().getReference().child("users").child(message.getSender().getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    sender = dataSnapshot.getValue(User.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
            friends.add(message.getSender());
            currUser.setFriends(friends);
            FireBase.getDatabase().child("users").child(FireBase.getCurrentUser().getUid()).setValue(currUser);

        }
    }
}



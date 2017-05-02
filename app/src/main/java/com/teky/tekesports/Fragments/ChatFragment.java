package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.teky.tekesports.Adapters.ChatAdapter;
import com.teky.tekesports.Model.Message;
import com.teky.tekesports.R;
import com.teky.tekesports.Utils.ChatControls;
import com.teky.tekesports.Utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.teky.tekesports.Utils.CurrentUser.currUser;
import static com.teky.tekesports.Utils.FireBase.getDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static List<Message> messages = new ArrayList<>();
    private static ChatAdapter chatAdapter;
    private boolean isListenerAdded = false;
    private DatabaseReference firebaseData = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.chat_view)
    RecyclerView chatView;

    @Bind(R.id.chat_screen)
    LinearLayout chatScreen;

    @Bind(R.id.send_message)
    Button sendButton;

    @Bind(R.id.chat_box)
    EditText messageField;


    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        firebaseData = FirebaseDatabase.getInstance().getReference();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currUser != null) {
                    Message message = new Message(currUser, currUser.getUserID(), false, messageField.getText().toString(), Utils.chatDate(new Date()));
                    ChatControls chatControls = new ChatControls(currUser, message);
                    messageField.setText(null);
                    chatControls.sendMessage();

                } else {
                    Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!isListenerAdded) {
            firebaseData.addChildEventListener(childEventListener);
            isListenerAdded = true;
            chatAdapter = new ChatAdapter(messages, getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            chatView.setLayoutManager(linearLayoutManager);
            chatView.setAdapter(chatAdapter);
        }
//        mDatabase.child("users").child(getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user = dataSnapshot.getValue(User.class);
//                if(user == null) {
//                    return;
//                } else {
//                    user.setOnline(true);
//                    mDatabase.child("users").child(getCurrentUser().getUid()).setValue(user);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        if (getArguments() == null) {
            v =inflater.inflate(R.layout.fragment_chat_view, container, false);
            Toast.makeText(getActivity(), "Chat View", Toast.LENGTH_SHORT).show();
        } else {
            v = inflater.inflate(R.layout.fragment_chat_game_view, container, false);
            Toast.makeText(getActivity(), "Game Chat View", Toast.LENGTH_SHORT).show();
        }
        ButterKnife.bind(this, v);
        return v;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @OnClick(R.id.send_message)
    public void sendMessage(){
        if (currUser != null) {
            Message message = new Message(currUser, currUser.getUserID(), false, messageField.getText().toString(), Utils.chatDate(new Date()));
            ChatControls chatControls = new ChatControls(currUser, message);
            messageField.setText(null);
            chatControls.sendMessage();

        } else {
            Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
        }
    }


    ChildEventListener childEventListener = getDatabase().child("messages").limitToLast(20).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Gson gson = new Gson();
            final String string = gson.toJson(dataSnapshot.getValue());
            if (!string.equals("null")) {
                Message myClass = gson.fromJson(string, Message.class);
                if (!messages.contains(myClass)) {
                    messages.add(myClass);
                }
            }
            chatAdapter.message = messages;
            chatAdapter.notifyDataSetChanged();
            if(!chatView.equals(null)) {
                chatView.scrollToPosition(messages.size() - 1);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    @Override
    public void onPause() {
        if (isListenerAdded) {
            firebaseData.child("messages").removeEventListener(childEventListener);
        }
        super.onPause();  // Always call the superclass method first
    }
}

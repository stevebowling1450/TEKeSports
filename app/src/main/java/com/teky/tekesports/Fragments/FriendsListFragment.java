package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teky.tekesports.Adapters.FriendsListAdapter;
import com.teky.tekesports.Model.User;
import com.teky.tekesports.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.teky.tekesports.Utils.CurrentUser.currUser;

/**
 * Created by lennyhicks on 12/9/16.
 * toornament-android.
 */

public class FriendsListFragment extends Fragment {

    @Bind(R.id.friends_list)
    RecyclerView friendsView;
    private FriendsListAdapter friendsListAdapter;
    private final List<User> friendList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends_list, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendsListAdapter = new FriendsListAdapter(friendList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        friendsView.setLayoutManager(linearLayoutManager);
        friendsView.setAdapter(friendsListAdapter);
        getFriends();
    }

    private void getFriends() {
        if (currUser != null) {
            for (User friend : currUser.getFriends()) {
                if (friend != null) {
                    friendsListAdapter.friendList.add(friend);
                }
            }
            //friendsListAdapter.friendList = user.getFriends();
            friendsListAdapter.notifyDataSetChanged();
        }
    }
}

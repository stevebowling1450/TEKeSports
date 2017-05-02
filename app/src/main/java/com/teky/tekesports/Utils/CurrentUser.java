package com.teky.tekesports.Utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teky.tekesports.Model.User;

/**
 * Created by lennyhicks on 12/9/16.
 * toornament-android.
 */

public class CurrentUser {
    public static User currUser;

    public CurrentUser(User user) {
        currUser = user;
    }

    public static void getCurrentUser() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FireBase.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static User getUser() {

        return currUser;
    }

    public static void setUser(User user) {
        currUser = user;
    }

    public static void saveUser(User userUpdated) {
        Log.i("SAVING: ", userUpdated.getFavorites().size() + "");
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
        userDB.child("users").child(userUpdated.getUserID()).setValue(userUpdated);
    }
}

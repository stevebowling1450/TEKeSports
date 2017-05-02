package com.teky.tekesports.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lennyhicks on 12/8/16.
 * toornament-android.
 */

public class FireBase {

    public static DatabaseReference mDatabase;
    public static DatabaseReference userDatabase;

    public FireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getUser(String userId) {
        if (userDatabase == null) {
            userDatabase = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("users")
                    .child(userId);
        }
        return userDatabase;
    }

    public static FirebaseUser getCurrentUser() {

        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static DatabaseReference getDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase;

    }

}

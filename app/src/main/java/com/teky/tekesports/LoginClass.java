package com.teky.tekesports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teky.tekesports.Model.User;
import com.teky.tekesports.Utils.FireBase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.teky.tekesports.Utils.CurrentUser.currUser;

/**
 * Created by JoshuaMabry on 12/5/16.
 * toornament-android.
 */

public class LoginClass extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";

    @Bind(R.id.register_button)
    Button register;

    @Bind(R.id.enter_password_login)
    EditText enterPassword;

    @Bind(R.id.enter_email_login)
    EditText enterEmail;

    @Bind(R.id.submit_button_login)
    ActionProcessButton signInButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(LoginClass.this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @OnClick(R.id.register_button)
    void registerClicked() {
        openRegisterScreen();
    }

    private void openRegisterScreen() {
        Intent intent = new Intent(getApplicationContext(), SignupClass.class);
        startActivity(intent);
    }

    @OnClick(R.id.submit_button_login)
    void submitClicked() {
        signInButton.setMode(ActionProcessButton.Mode.ENDLESS);

        signInButton.setProgress(1);
        //Create user account from input
        String email = enterEmail.getText().toString();
        String password = enterPassword.getText().toString();
        createAccount(email, password);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginClass.this, "Authorization Failed",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {

                            //Return to previous screen after registering
                            openMainActivity();
                        }

                        // ...
                    }
                });

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = enterEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            enterEmail.setError("Required.");
            valid = false;
        } else {
            enterEmail.setError(null);
        }

        String password = enterPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            enterPassword.setError("Required.");
            valid = false;
        } else {
            enterPassword.setError(null);
        }

        return valid;
    }

    private void openMainActivity() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginClass.class);
            startActivity(intent);
        } else {
            FirebaseDatabase.getInstance().getReference().child("users").child(FireBase.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currUser = dataSnapshot.getValue(User.class);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

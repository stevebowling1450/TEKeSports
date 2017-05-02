package com.teky.tekesports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teky.tekesports.Model.User;
import com.teky.tekesports.Utils.FireBase;

import java.text.Collator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JoshuaMabry on 12/5/16.
 * toornament-android.
 */

public class SignupClass extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private final List<String> userNames = new LinkedList<>();
    private final Set<String> userNameSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,20}$";
    private boolean success = false;

    @Bind(R.id.btn_signup)
    Button submitRegister;

    @Bind(R.id.input_email)
    EditText enterEmail;

    @Bind(R.id.input_password)
    EditText enterPassword;

    @Bind(R.id.input_name)
    EditText userName;

    @Bind(R.id.link_login)
    TextView alreadyUser;

    @Bind(R.id.passwordLayout)
    LinearLayout passwordCheck;

    @Bind(R.id.passwordCheckLength)
    TextView passwordLength;

    @Bind(R.id.passwordCheckAlpha)
    TextView passwordAlpha;

    @Bind(R.id.passwordCheckNumber)
    TextView passwordNumber;

    @Bind(R.id.input_password_confirm)
    EditText passwordConfirm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        ButterKnife.bind(SignupClass.this);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously();

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

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    String takenName = users.child("username").getValue().toString();
                    if (takenName != null) {
                        userNames.add(takenName);
                    }
                }
                userNameSet.addAll(userNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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


    @OnClick(R.id.btn_signup)
    void registerClicked() {

        //Create user account from input
        String email = enterEmail.getText().toString();
        String password = enterPassword.getText().toString();
        createAccount(email, password);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupClass.this, "Authorization Failed",
                                        Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {

                                //Return to previous screen after registering
                                openSignInScreen();
                            }
                        }
                    });
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = enterEmail.getText().toString();
        String user = userName.getText().toString();
        String password = enterPassword.getText().toString();

        final Collator ignoreCase = Collator.getInstance();
        ignoreCase.setStrength(Collator.SECONDARY);

        if (user.isEmpty()) {
            userName.setError("Username Required.");
        } else if (userNameSet.contains(user)) {
            userName.setError("Username Taken.");
        } else {
            userName.setError(null);
        }

        if (email.isEmpty()) {
            enterEmail.setError("Email Required.");
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            enterEmail.setError("Email Invalid.");
        } else {
            enterEmail.setError(null);
        }

        if (password.isEmpty()) {
            enterPassword.setError("Required.");
            valid = false;
        } else if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            passwordCheck.setVisibility(View.VISIBLE);

            if (password.length() < 8) {
                passwordLength.setTextColor(Color.RED);
            } else {
                passwordLength.setTextColor(Color.GREEN);
            }

            String ALPHA_CHECK = "(?=.*[a-zA-Z])";
            Pattern pattern = Pattern.compile(ALPHA_CHECK);
            Matcher matcher = pattern.matcher(password);
            if (!matcher.find()) {
                passwordAlpha.setTextColor(Color.RED);
            } else {
                passwordAlpha.setTextColor(Color.GREEN);
            }

            String DIGIT_CHECK = "(?=.*\\d)";
            pattern = Pattern.compile(DIGIT_CHECK);
            matcher = pattern.matcher(password);
            if (!matcher.find()) {
                passwordNumber.setTextColor(Color.RED);
            } else {
                passwordNumber.setTextColor(Color.GREEN);
            }
            enterPassword.setError("Password Invalid.");
            valid = false;
        } else {
            passwordLength.setTextColor(Color.GREEN);
            passwordAlpha.setTextColor(Color.GREEN);
            passwordNumber.setTextColor(Color.GREEN);


        }
        if (password.matches(passwordConfirm.getText().toString())) {
            enterPassword.setError(null);
        } else {
            passwordConfirm.setError("Passwords must match.");
            valid = false;
        }
        return valid;
    }

    private void openSignInScreen() {
        success = true;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User();
        user.setUsername(userName.getText().toString());
        user.setEmail(enterEmail.getText().toString());
        user.setUserID(FireBase.getCurrentUser().getUid());


        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(user.getUserID())
                .setValue(user);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getUsername())
                .build();

        if (currentUser != null) {
            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            }
                        }
                    });

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        if (!success) {
            if (mAuth.getCurrentUser() != null) {
                mAuth.getCurrentUser().delete();
                mAuth.signOut();
            }
        }
        super.onPause();
    }

    @OnClick(R.id.link_login)
    public void alreadyUserTapped() {
        Intent intent = new Intent(this, LoginClass.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginClass.class);
        startActivity(intent);
    }
}

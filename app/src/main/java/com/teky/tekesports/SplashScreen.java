package com.teky.tekesports;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teky.tekesports.Utils.CurrentUser;

import static com.teky.tekesports.Components.Constants.getAllGames;

public class SplashScreen extends AppCompatActivity {


    ProgressBar pgr;
    int progress = 0;
    final Handler h = new Handler();
    private final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spash_screen);
        Thread myThread = new Thread() {
            @Override
            public void run() {

            }
        };

        myThread.start();
        pgr = (ProgressBar) findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadList();
                for (int i = 0; i < 100; i++) {
                    progress += 1;
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            pgr.setProgress(progress);
                            if (progress == pgr.getMax()) {
                                pgr.setVisibility(View.GONE);
                                authenticated();
                            }
                        }
                    });

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void authenticated() {
        if (mUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginClass.class);
            startActivity(intent);
            finish();
        } else {
            CurrentUser.getCurrentUser();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("title", "News");
            startActivity(intent);
            finish();
        }
    }


    private void loadList() {

        getAllGames("RTS");
        getAllGames("FPS");
        getAllGames("MOBA");
        getAllGames("Sports");
        getAllGames("Fighting");
        getAllGames("Other");
    }
}

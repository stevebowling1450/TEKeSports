package com.teky.tekesports;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;


import com.teky.tekesports.Fragments.FavoriteFragment;
import com.teky.tekesports.Fragments.GenreFragment;
import com.teky.tekesports.Fragments.PopularGamesFragment;
import com.teky.tekesports.Fragments.SettingsFragment;
import com.teky.tekesports.Fragments.TwitterFeedFragment;
import com.teky.tekesports.Model.User;
import com.teky.tekesports.Utils.FireBase;


import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.teky.tekesports.Components.Constants.FAVORITESFRAGMENT;
import static com.teky.tekesports.Components.Constants.GENREFIRSTFRAGMENT;
import static com.teky.tekesports.Components.Constants.POPULARFRAGMENT;
import static com.teky.tekesports.Components.Constants.TWITTERFRAGMENT;
import static com.teky.tekesports.Utils.CurrentUser.getUser;

//import com.teky.tekesports.Fragments.ChatFragment;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences sharedPrefs;
    public static final String API_BASE_URL = "https://api.toornament.com/";
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private IProfile profile;

    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 600;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.layout_main_frame)
    FrameLayout frameLayout;
    private boolean screenSetUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ExceptionHandler.init(this, SplashScreen.class);
        sharedPrefs = getSharedPreferences("Tournament", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User currentLoggedIn = getUser();

        if (currentLoggedIn != null && currentLoggedIn.getFirstLogin()) {
            openNewView(new GenreFragment(), GENREFIRSTFRAGMENT);
        } else {
            openNewView(new TwitterFeedFragment(), TWITTERFRAGMENT);
        }

        setSupportActionBar(toolbar);

        if (currentUser != null) {
            profile = new ProfileDrawerItem().withName(currentUser.getDisplayName()).withEmail(currentUser.getEmail()).withIcon(getIcon(MaterialDrawableBuilder.IconValue.FACE_PROFILE));
        }
        // Create the AccountHeader
        buildHeader(false, savedInstanceState);
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar).withHeaderPadding(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_news).withIcon(R.drawable.news).withIdentifier(1),
                        //new SecondaryDrawerItem().withName(R.string.drawer_item_chat).withIcon(getIcon(MaterialDrawableBuilder.IconValue.WECHAT)).withIdentifier(2),
                        //new SecondaryDrawerItem().withName(R.string.drawer_item_friends).withIcon(getIcon(MaterialDrawableBuilder.IconValue.FACE)).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_favorites).withIcon(R.drawable.fav_folder).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_popular).withIcon(R.drawable.prize).withIdentifier(5)
                        , new SecondaryDrawerItem().withName(R.string.game_by_genre).withIcon(R.drawable.all_games).withIdentifier(6)
                )
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity

                        MainActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(R.drawable.settings).withIdentifier(7)
                                .withSubItems(
                                        new SecondaryDrawerItem().withName("Feedback").withIcon(R.drawable.feedback).withIdentifier(8),
                                        new SecondaryDrawerItem().withName("About").withIcon(R.drawable.about).withIdentifier(9)),

                        new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(R.drawable.logout).withIdentifier(10)
                )
                .withSavedInstance(savedInstanceState)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.drawable.item_background_2))
                .build();

        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Fragment fragment = new Fragment();
                int drawerId = ((int) drawerItem.getIdentifier());
                String type = "";

                switch (drawerId) {
                    case 1:
                        toolbar.setTitle("News");
                        fragment = new TwitterFeedFragment();
                        type = TWITTERFRAGMENT;
                        break;
//                    case 2:
//                        toolbar.setTitle("Chat");
//                        fragment = new ChatFragment();
//                        Toast.makeText(MainActivity.this, "We don't need that anymore", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        toolbar.setTitle("Friends");
//                        fragment = new FriendsListFragment();
//                        break;
                    case 4:
                        toolbar.setTitle("Favorite Games");
                        fragment = new FavoriteFragment();
                        type = FAVORITESFRAGMENT;
                        break;
                    case 5:
                        toolbar.setTitle("Popular Games");
                        fragment = new PopularGamesFragment();
                        type = POPULARFRAGMENT;
                        break;
                    case 6:
                        toolbar.setTitle("All Games");
                        fragment = new GenreFragment();
                        type = GENREFIRSTFRAGMENT;
                        break;
                    case 7:
                        toolbar.setTitle("Settings");
                        fragment = new SettingsFragment();
                        type = "Settings";
                        break;

                    case 10:
                        FireBase.signOut();
                        Intent i = new Intent(MainActivity.this, LoginClass.class);
                        startActivity(i);
                        break;
                }

                openNewView(fragment, type);

                return false;

            }
        });
    }


    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.google_account_header6)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(getIcon(MaterialDrawableBuilder.IconValue.ACCOUNT_SETTINGS_VARIANT))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getIcon(MaterialDrawableBuilder.IconValue.FACE_PROFILE));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

    }

    public void openNewView(Fragment fragment, String type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.layout_main_frame);
        // Return if the class are the same
        //if(fragmentManager.getBackStackEntryCount() > 0) {
        if (currentFragment != null) {
            if (currentFragment.getClass() != null) {
                if (currentFragment.getClass().equals(fragment.getClass())) return;
            }
        }
        long now = System.currentTimeMillis();

        if (now - mLastClickTime < CLICK_TIME_INTERVAL && screenSetUp) {
            return;
        }
        mLastClickTime = now;


        if (getSupportFragmentManager().findFragmentByTag(type) != null) {
            fragmentManager.popBackStackImmediate(type, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);

        if (screenSetUp) {
            fragmentTransaction.add(R.id.layout_main_frame, fragment, type);
            fragmentTransaction.addToBackStack(type);
        } else {
            fragmentTransaction.replace(R.id.layout_main_frame, fragment, type);
            screenSetUp = true;
        }
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()){
            result.closeDrawer();
            return;
        }
        result.setSelection(0);

        String title = "";

        for (Fragment frag : getSupportFragmentManager().getFragments()) {
            try {
                if (frag.getArguments().getString("title") != null) {
                    title = frag.getArguments().getString("title");
                }
            } catch (Exception e) {
                Log.i("Log", "Fail");
            }
        }

        if (getSupportActionBar() != null) {
             getSupportActionBar().setTitle(title);
            getSupportActionBar().setTitle(null);
        }

        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {

//            if (title != null) {
//                getSupportActionBar().setTitle(title);
//            }
            getFragmentManager().popBackStackImmediate();

        }

    }

    public Drawable getIcon(MaterialDrawableBuilder.IconValue icon) {
        // The method returns a MaterialDrawable, but as it is private to the builder you'll have to store it as a regular Drawable ;)

        return MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(icon) // provide an icon
                .setColor(Color.GRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build(); // Finally call build
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
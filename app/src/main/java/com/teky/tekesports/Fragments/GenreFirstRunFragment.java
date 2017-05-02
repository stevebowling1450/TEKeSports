package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teky.tekesports.Adapters.GameBinderAdapter;
import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.teky.tekesports.Components.Constants.FPS;
import static com.teky.tekesports.Components.Constants.Fighting;
import static com.teky.tekesports.Components.Constants.MOBA;
import static com.teky.tekesports.Components.Constants.Other;
import static com.teky.tekesports.Components.Constants.RTS;
import static com.teky.tekesports.Components.Constants.Sports;
import static com.teky.tekesports.Utils.CurrentUser.currUser;
import static com.teky.tekesports.Utils.CurrentUser.saveUser;

/**
 * Created by lennyhicks on 12/9/16.
 * toornament-android.
 */

public class GenreFirstRunFragment extends Fragment {

    @Bind(R.id.fighting_button)
    TextView fightingButton;

    @Bind(R.id.moba_button)
    TextView mobaButton;

    @Bind(R.id.other_button)
    TextView otherButton;

    @Bind(R.id.fps_button)
    TextView fpsButton;

    @Bind(R.id.sports_button)
    TextView sportsButton;

    @Bind(R.id.rts_button)
    TextView rtsButton;

    @Bind(R.id.favorite_list)
    RecyclerView gameList;

    @Bind(R.id.favorites_view)
    LinearLayout genreScreen;

    @Bind(R.id.genre_left_view)
    LinearLayout genreLeftScreen;

    @Bind(R.id.genre_right_view)
    LinearLayout genreRightScreen;

    @Bind(R.id.placeholder)
    LinearLayout placeHolder;

    private GameBinderAdapter gameAdapter;
    private boolean screenOpen = false;
    private List<Disciplines> list;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_genre_first_view, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (currUser.getFirstLogin()){
            currUser.setFirstLogin(false);
            saveUser(currUser);
        }
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        gameList.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        gameAdapter = new GameBinderAdapter(list, getActivity(), getFragmentManager());
        gameList.setAdapter(gameAdapter);
        gameAdapter.getFilter().filter("");


    }


    @OnClick(R.id.fighting_button)
    public void fightTapped() {
        list = Fighting;
        gameAdapter.notifyDataSetChanged();
        openRightView();
        fightingButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));
    }

    @OnClick(R.id.moba_button)
    public void mobaTapped() {
        list = MOBA;
        gameAdapter.notifyDataSetChanged();
        openRightView();
        mobaButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));

    }

    @OnClick(R.id.other_button)
    public void otherTapped() {
        list = Other;
        gameAdapter.notifyDataSetChanged();
        openRightView();
        otherButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));

    }

    @OnClick(R.id.fps_button)
    public void fpsTapped() {
        list = FPS;
        gameAdapter.notifyDataSetChanged();
        openLeftView();
        fpsButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));
    }

    @OnClick(R.id.sports_button)
    public void sportsTapped() {
        list = Sports;
        gameAdapter.notifyDataSetChanged();
        openLeftView();
        sportsButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));
    }

    @OnClick(R.id.rts_button)
    public void rtsTapped() {
        list = RTS;
        gameAdapter.notifyDataSetChanged();
        openLeftView();
        rtsButton.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));
    }

    private void openLeftView() {
        resetTabs();
        if (!screenOpen) {
            genreRightScreen.setVisibility(View.GONE);
            placeHolder.setVisibility(View.GONE);
            genreScreen.setVisibility(View.VISIBLE);
            screenOpen = true;
        } else {
            genreRightScreen.setVisibility(View.VISIBLE);
            placeHolder.setVisibility(View.VISIBLE);
            genreScreen.setVisibility(View.GONE);
            screenOpen = false;
        }
    }

    private void openRightView() {
        resetTabs();
        if (!screenOpen) {
            genreLeftScreen.setVisibility(View.GONE);
            placeHolder.setVisibility(View.GONE);
            genreScreen.setVisibility(View.VISIBLE);
            screenOpen = true;
        } else {
            genreLeftScreen.setVisibility(View.VISIBLE);
            placeHolder.setVisibility(View.VISIBLE);
            genreScreen.setVisibility(View.GONE);
            screenOpen = false;
        }
    }

    private void resetTabs() {

        gameAdapter = new GameBinderAdapter(list, getActivity(), getFragmentManager());
        gameList.setAdapter(gameAdapter);
        gameAdapter.getFilter().filter("");
        gameAdapter.notifyDataSetChanged();

        fightingButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
        fpsButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
        mobaButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
        otherButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
        rtsButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
        sportsButton.setBackgroundColor(getResources().getColor(R.color.tw__transparent));
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }
}

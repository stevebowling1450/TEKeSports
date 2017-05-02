package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.teky.tekesports.Adapters.GameBinderAdapter;
import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 12/5/16.
 * toornament-android.
 */

public class FavoriteFragment extends Fragment {
    private GameBinderAdapter gameAdapter;

    @Bind(R.id.nav_game_fav)
    RecyclerView gameList;

    @Bind(R.id.search)
    EditText search;

    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;

    private static ArrayList<Disciplines> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fav_screen, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        gameList.setLayoutManager(linearLayoutManager);
        if (list.size() == 0) {
            setupList();
        } else {
            setupView();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (gameAdapter == null) {
                    gameAdapter = new GameBinderAdapter(list, getActivity(), getActivity().getSupportFragmentManager());
                    gameAdapter.favoriteFiltered = true;
                    gameAdapter.notifyDataSetChanged();
                } else {
                    gameAdapter.getFilter().filter(charSequence);
                    gameAdapter.favoriteFiltered = true;
                    gameAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setupList() {
        RestClient restClient = new RestClient();
        restClient.getApiService().getGames().enqueue(new Callback<Disciplines[]>() {
            @Override
            public void onResponse(Call<Disciplines[]> call, Response<Disciplines[]> response) {
                list = new ArrayList<>(Arrays.asList(response.body()));
                setupView();

            }

            @Override
            public void onFailure(Call<Disciplines[]> call, Throwable t) {

            }
        });
    }

    private void setupView() {
        gameAdapter = new GameBinderAdapter(list, getActivity(), getActivity().getSupportFragmentManager());
        gameAdapter.favoriteFiltered = true;
        gameList.setAdapter(gameAdapter);
        loadingBar.setVisibility(View.GONE);
        gameList.setVisibility(View.VISIBLE);
        gameAdapter.getFilter().filter("");
        gameAdapter.notifyDataSetChanged();

//        if (gameAdapter.getItemCount() > 10) {
        search.setVisibility(View.VISIBLE);
//        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }

}









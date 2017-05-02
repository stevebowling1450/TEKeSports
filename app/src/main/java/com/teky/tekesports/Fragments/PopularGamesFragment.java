package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.teky.tekesports.Components.Constants;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jessemaynard on 12/13/16.
 * toornament-android.
 */

public class PopularGamesFragment extends Fragment {

    @Bind(R.id.dota_2_image)
    ImageView dota2;

    @Bind(R.id.league_of_legends_image)
    ImageView league;

    @Bind(R.id.cs_go_image)
    ImageView csgo;

    @Bind(R.id.hearthstone_image)
    ImageView hearthstone;

    @Bind(R.id.rocket_league_image)
    ImageView rocketLeague;

    @Bind(R.id.vain_glory_image)
    ImageView vainGlory;

    @Bind(R.id.super_smash_bros_image)
    ImageView superSmashBros;

    @Bind(R.id.fifa_image)
    ImageView fifa;

    @Bind(R.id.clash_royale_image)
    ImageView clashRoyal;

    @Bind(R.id.cod_advanced_warfare_image)
    ImageView codAw;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discipline_list_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setupView();

    }

    private void setupView() {
        Glide.with(this).load(R.drawable.dota_2_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(dota2);
        Glide.with(this).load(R.drawable.cs_go_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(csgo);
        Glide.with(this).load(R.drawable.rocket_league_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(rocketLeague);
        Glide.with(this).load(R.drawable.smash_bros_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(superSmashBros);
        Glide.with(this).load(R.drawable.cod_advanced_warfare_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(codAw);
        Glide.with(this).load(R.drawable.vain_glory_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(vainGlory);
        Glide.with(this).load(R.drawable.clash_royale_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(clashRoyal);
        Glide.with(this).load(R.drawable.fifa_logo).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(fifa);
        Glide.with(this).load(R.drawable.hearthstone_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(hearthstone);
        Glide.with(this).load(R.drawable.lol_screen1).override(150,255).diskCacheStrategy(DiskCacheStrategy.NONE).into(league);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.dota_2_image, R.id.cs_go_image, R.id.rocket_league_image, R.id.super_smash_bros_image, R.id.cod_advanced_warfare_image, R.id.vain_glory_image, R.id.clash_royale_image, R.id.fifa_image, R.id.hearthstone_image, R.id.league_of_legends_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dota_2_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.DOTA2, "Dota 2"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.cs_go_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.COUNTERSTRIKE_GO, "Counter Strike GO"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.rocket_league_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.ROCKETLEAGUE, "Rocket League"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.super_smash_bros_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.SUPERSMASHBROS, "Super Smash Brothers"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.cod_advanced_warfare_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.COD_ADVANCED_WAREFARE, "Call Of Duty AW"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.vain_glory_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.VAINGLORY, "Vainglory"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.clash_royale_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.CLASH_ROYALE, "Clash Royale"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.fifa_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.FIFA16, "Fifa 16"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.hearthstone_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.HEARTHSTONE, "Hearthstone"), Constants.TOURNAMENTFRAGMENT);
                break;
            case R.id.league_of_legends_image:
                ((MainActivity) getActivity()).openNewView(TournamentFragment.newInstance(Constants.LEAUGEOFLEGENDS, "League of Legends"), Constants.TOURNAMENTFRAGMENT);
                break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }
}

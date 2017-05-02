package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.teky.tekesports.Adapters.GameBinderAdapter;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.R;

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


/**
 * Created by lennyhicks on 12/15/16.
 * toornament-android.
 */

public class GenreFragment extends Fragment {


    int height;
    int width;
    private static String TITLE = "title";
    private static String title;

    @Bind(R.id.logo_fps)
    ImageView mLogoFps;
    @Bind(R.id.view_fps)
    RecyclerView mViewFps;
    @Bind(R.id.logo_moba)
    ImageView mLogoMoba;
    @Bind(R.id.view_moba)
    RecyclerView mViewMoba;
    @Bind(R.id.logo_fighting)
    ImageView mLogoFighting;
    @Bind(R.id.view_fighting)
    RecyclerView mViewFighting;
    @Bind(R.id.logo_rts)
    ImageView mLogoRts;
    @Bind(R.id.view_rts)
    RecyclerView mViewRts;
    @Bind(R.id.logo_sports)
    ImageView mLogoSports;
    @Bind(R.id.view_sports)
    RecyclerView mViewSports;
    @Bind(R.id.logo_other)
    ImageView mLogoOther;
    @Bind(R.id.view_other)
    RecyclerView mViewOther;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_genre_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            ((MainActivity) getActivity()).getToolbar().setTitle(title);
        }

        width = 600;
        if (view != null) {
            if (view.getDisplay() != null) {
                Display display = view.getDisplay();
                if (display != null) {
                    DisplayMetrics metrics = new DisplayMetrics();
                    try {
                        display.getMetrics(metrics);
                        height = metrics.heightPixels;
                        int h1 = ((MainActivity) getActivity()).getToolbar().getHeight();
                        height = (int) ((height - (h1 * 1.5)) / 6);
                        width = metrics.widthPixels;
                    } catch (Exception e){
                        height = 200;
                    }
                } else {
                    height = 200;
                }
            } else {
                height = 200;
            }
        } else {
            height = 200;
        }

        Glide.with(this).load(R.drawable.logo_fps).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoFps);
        Glide.with(this).load(R.drawable.logo_fighting).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoFighting);
        Glide.with(this).load(R.drawable.logo_other).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoOther);
        Glide.with(this).load(R.drawable.logo_rts).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoRts);
        Glide.with(this).load(R.drawable.logo_sports).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoSports);
        Glide.with(this).load(R.drawable.logo_moba).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).atMost().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mLogoMoba);

        setupView();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.logo_fps, R.id.logo_moba, R.id.logo_fighting, R.id.logo_rts, R.id.logo_sports, R.id.logo_other})
    public void onClick(View view) {
        view.setActivated(true);
        switch (view.getId()) {
            case R.id.logo_fps:
                setupListView(mViewFps, FPS);
                break;
            case R.id.logo_moba:
                setupListView(mViewMoba, MOBA);
                break;
            case R.id.logo_fighting:
                setupListView(mViewFighting, Fighting);
                break;
            case R.id.logo_rts:
                setupListView(mViewRts, RTS);
                break;
            case R.id.logo_sports:
                setupListView(mViewSports, Sports);
                break;
            case R.id.logo_other:
                setupListView(mViewOther, Other);
                break;
        }
    }

    public void setupView() {

        mLogoFighting.getLayoutParams().height = height;
        mLogoFighting.getLayoutParams().width = width;
        mLogoFps.getLayoutParams().height = height;
        mLogoFps.getLayoutParams().width = width;
        mLogoMoba.getLayoutParams().height = height;
        mLogoMoba.getLayoutParams().width = width;
        mLogoOther.getLayoutParams().height = height;
        mLogoOther.getLayoutParams().width = width;
        mLogoRts.getLayoutParams().height = height;
        mLogoRts.getLayoutParams().width = width;
        mLogoSports.getLayoutParams().height = height;
        mLogoSports.getLayoutParams().width = width;



    }

    private void setupListView(final RecyclerView rec, List<Disciplines> list) {
        if (rec.getVisibility() == View.GONE) {
            rec.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rec.setLayoutManager(linearLayoutManager);
            GameBinderAdapter gameAdapter = new GameBinderAdapter(list, getActivity(), getFragmentManager());
            rec.setAdapter(gameAdapter);
            gameAdapter.getFilter().filter("");
        } else {
            rec.setVisibility(View.GONE);
        }
        int[] recviews = {R.id.view_fps, R.id.view_moba, R.id.view_fighting, R.id.view_rts, R.id.view_sports, R.id.view_other};
        for (Integer recy : recviews) {
            if (rec.getId() != recy) {
                getActivity().findViewById(recy).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }

    public static GenreFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        GenreFragment fragment = new GenreFragment();
        fragment.setArguments(args);
        return fragment;
    }
}


package com.teky.tekesports.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teky.tekesports.Adapters.GameTypeListAdapter;
import com.teky.tekesports.Model.GameTypes;
import com.teky.tekesports.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lennyhicks on 12/12/16.
 * toornament-android.
 */

public class GameSelectionDialog extends DialogFragment {
    private String discipline;
    private String fullName;
    private final List<GameTypes> types = new ArrayList<>();

    @Bind(R.id.game_type_recycler)
    RecyclerView gameTypes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_game_type, container, false);
        getDialog().setTitle(fullName);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void setupView(String discipline, String fullName) {
        this.discipline = discipline;
        this.fullName = fullName;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        types.add(new GameTypes("FPS"));
        types.add(new GameTypes("RTS"));
        types.add(new GameTypes("Fighting"));
        types.add(new GameTypes("MOBA"));
        types.add(new GameTypes("Sports"));
        types.add(new GameTypes("Other"));

        GameTypeListAdapter adapter = new GameTypeListAdapter(types, getActivity(), new GameTypes(discipline, fullName));
        LinearLayoutManager gameTypeLayoutManager = new LinearLayoutManager(getActivity());
        gameTypes.setLayoutManager(gameTypeLayoutManager);
        gameTypes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gameTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}

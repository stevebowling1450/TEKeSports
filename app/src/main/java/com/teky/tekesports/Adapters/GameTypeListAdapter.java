package com.teky.tekesports.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teky.tekesports.Model.GameTypes;
import com.teky.tekesports.R;
import com.teky.tekesports.Utils.GameTypeList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lennyhicks on 12/9/16.
 * toornament-android.
 */

public class GameTypeListAdapter extends RecyclerView.Adapter<GameTypeListAdapter.TypeHolder> {

    private final GameTypes currentGame;
    public final List<GameTypes> gameTypeList;
    private final Context context;
    private static final ArrayList<View> views = new ArrayList<>();

    public GameTypeListAdapter(List<GameTypes> gameType, Context context, GameTypes currentGame) {
        this.gameTypeList = gameType;
        this.context = context;
        this.currentGame = currentGame;
    }

    @Override
    public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item_game_type, parent, false);
        return new TypeHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(TypeHolder holder, int position) {
        if (position < gameTypeList.size()) {
            GameTypes mess = gameTypeList.get(position);
            holder.bindGame(mess, currentGame);

        }
    }

    @Override
    public int getItemCount() {
        return gameTypeList.size();
    }

    class TypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.game_type_name)
        TextView gameType;

        private GameTypes game;
        private GameTypes currentGame;

        public TypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            views.add(itemView);
        }

        // Adds the data to the view.
        public void bindGame(GameTypes game, GameTypes currentGame) {
            // this.gameTypeList = user.getGameType();
            if(gameType != null) {
                gameType.setText(game.getGameType());
            }
            this.game = game;
            this.currentGame = currentGame;
        }

        @Override
        public void onClick(View view) {
            GameTypeList.addToList(game.getGameType(), currentGame, game);
            for (View viewss : views) {
                Log.i("VIEW", viewss.toString());
                viewss.setBackgroundColor(Color.RED);
            }
            view.setBackgroundColor(Color.GREEN);
        }
    }
}
package com.teky.tekesports.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teky.tekesports.Components.Constants;
import com.teky.tekesports.Fragments.GameFragment;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.Model.Match;
import com.teky.tekesports.Model.Stream;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.teky.tekesports.Components.Settings.showWinners;


/**
 * Created by jessemaynard on 12/1/16.
 * toornament-android.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> {
    public ArrayList<Match> matches;
    private final Context context;

    private final FragmentManager fragmentManager;

    public MatchAdapter(ArrayList<Match> matches, Context context, FragmentManager fragmentManager) {
        this.matches = matches;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MatchAdapter.MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
        return new MatchHolder(inflateView, fragmentManager);
    }

    @Override
    public void onBindViewHolder(MatchAdapter.MatchHolder holder, int position) {
        if (position < matches.size()) {
            Match match = matches.get(position);
            holder.bindMatch(match);
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class MatchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.opponents_one)
        TextView opponentsOne;

        @Bind(R.id.opponents_two)
        TextView opponentsTwo;

        @Bind(R.id.opponents_one_flag)
        ImageView teamOneFlag;

        @Bind(R.id.opponents_two_flag)
        ImageView teamTwoFlag;

        @Bind(R.id.match_id)
        TextView matchId;

        @Bind(R.id.match_date)
        TextView matchDate;

        @Bind(R.id.video_view)
        ImageView videoView;


        private String id;
        private Match match;
        private boolean isChecked = false;
        private FragmentManager fragmentManager;

        public MatchHolder(View itemView, FragmentManager fragmentManager) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.fragmentManager = fragmentManager;
        }

        // Adds the data to the view.
        public void bindMatch(final Match match) {
            matchId.setText(match.getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d hh:mm aaa", Locale.getDefault());
            Date convertedDate;
            id = match.getId();
            if (!isChecked) {

                RestClient restClient = new RestClient();
                restClient.getApiService().getMatch(match.getTournamentId(), id).enqueue(new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        isChecked = true;
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStreams() != null) {
//                            try {
                                    for (Stream stream : response.body().getStreams()) {
                                        if (stream.getUrl().contains("twitch")) {
                                            videoView.setImageDrawable(getIcon(MaterialDrawableBuilder.IconValue.TWITCH));
                                        } else if (stream.getUrl().contains("youtube")) {
                                            videoView.setImageDrawable(getIcon(MaterialDrawableBuilder.IconValue.YOUTUBE_PLAY));
                                        }
                                        videoView.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            int teamOne = response.body().getOpponents().get(0).getScore();
                            int teamTwo = response.body().getOpponents().get(1).getScore();
                            if (response.body().getStatus().equals("completed")) {
                                if (showWinners) {
                                    if (teamOne > teamTwo) {
                                        opponentsTwo.setAlpha(0.6f);
                                    } else if (teamTwo > teamOne) {
                                        opponentsOne.setAlpha(0.6f);
                                    } else {
                                        opponentsOne.setAlpha(0.6f);
                                        opponentsTwo.setAlpha(0.6f);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {

                    }
                });
            }
            this.match = match;
            try {
                opponentsOne.setText(match.getOpponents().get(0).getParticipant().getName());
            } catch (Exception e) {
                opponentsOne.setText(R.string.tbd);
            }
            try {
                opponentsTwo.setText(match.getOpponents().get(1).getParticipant().getName());
            } catch (NullPointerException e) {
                opponentsTwo.setText(R.string.tbd);
            }

            if (match.getDate() == null) {
                if (match.getStatus().equals("completed")) {
                    matchDate.setText(R.string.completed);
                } else {
                    matchDate.setText(R.string.tbd);
                }
            } else {

                try {
                    convertedDate = dateFormat.parse(match.getDate());
                    matchDate.setText(sdf.format(convertedDate));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            matchId.setText(match.getId());
            if (match.getOpponents().get(0).getParticipant() != null) {
                if (match.getOpponents().get(0).getParticipant().getCountry() != null) {
                    String countryOne = match.getOpponents().get(0).getParticipant().getCountry().toLowerCase();
                    int countryIdOne = context.getResources().getIdentifier(countryOne, "drawable", context.getPackageName());
                    teamOneFlag.setImageResource(countryIdOne);
                    if (!(countryIdOne > 0)) {
                        String icon = match.getDiscipline();
                        int iconValue = context.getResources().getIdentifier(icon + "_icon", "drawable", context.getPackageName());
                        teamOneFlag.setImageResource(iconValue);
                    } else {
                        setupFlag(match, teamOneFlag, teamTwoFlag);
                    }
                } else {
                    setupFlag(match, teamOneFlag, teamTwoFlag);
                }
            } else {
                setupFlag(match, teamOneFlag, teamTwoFlag);
            }
            matchId.setText(match.getId());
            if (match.getOpponents().get(1).getParticipant() != null) {
                if (match.getOpponents().get(1).getParticipant().getCountry() != null) {
                    String countryTwo = match.getOpponents().get(1).getParticipant().getCountry().toLowerCase();
                    int countryIdTwo = context.getResources().getIdentifier(countryTwo, "drawable", context.getPackageName());
                    teamTwoFlag.setImageResource(countryIdTwo);
                    if (countryIdTwo > 0) {
                        String icon = match.getDiscipline();
                        int iconValue = context.getResources().getIdentifier(icon + "_icon", "drawable", context.getPackageName());
                        teamTwoFlag.setImageResource(iconValue);
                    } else {
                        setupFlag(match, teamOneFlag, teamTwoFlag);
                    }
                } else {
                    setupFlag(match, teamOneFlag, teamTwoFlag);
                }
            } else {
                setupFlag(match, teamOneFlag, teamTwoFlag);
            }
        }


        @Override
        public void onClick(View view) {
            GameFragment fragment;
            try {
                fragment = GameFragment.newInstance(id, match.getTournamentId());
                ((MainActivity) context).openNewView(fragment, Constants.GAMEFRAGMENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public Drawable getIcon(MaterialDrawableBuilder.IconValue icon) {
        // The method returns a MaterialDrawable, but as it is private to the builder you'll have to store it as a regular Drawable ;)

        return MaterialDrawableBuilder.with(fragmentManager.getFragments().get(1).getActivity()) // provide a context
                .setIcon(icon) // provide an icon
                .setColor(Color.GRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build(); // Finally call build
    }

    private void setupFlag(Match match, ImageView teamOneFlag, ImageView teamTwoFlag) {
        String icon = match.getDiscipline();
        int iconValue = context.getResources().getIdentifier(icon + "_icon", "drawable", context.getPackageName());
        if (!(iconValue > 0)) {
            teamOneFlag.setImageResource(R.drawable.network_icon);
            teamOneFlag.setBackgroundResource(R.color.colorBlue);
            teamTwoFlag.setImageResource(R.drawable.network_icon);
            teamTwoFlag.setBackgroundResource(R.color.colorOrange);
        } else {
            teamOneFlag.setImageResource(iconValue);
            teamTwoFlag.setImageResource(iconValue);
        }
    }
}

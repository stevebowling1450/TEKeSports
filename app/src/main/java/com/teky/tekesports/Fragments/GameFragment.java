package com.teky.tekesports.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teky.tekesports.Model.Logo;
import com.teky.tekesports.Model.Match;
import com.teky.tekesports.Model.Opponent;
import com.teky.tekesports.Model.Participant;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.teky.tekesports.Components.Settings.showWinners;


public class GameFragment extends Fragment {

    private static final String GAMEID = "gameId";
    private static final String TOURNID = "tournId";
    private String tournId;
    private String matchId;
    boolean customsLogos = false;
    boolean firstLoaded = false;

    @Bind(R.id.video_view)
    WebView video;

    @Bind(R.id.team_one_flag)
    ImageView flagOne;

    @Bind(R.id.team_two_flag)
    ImageView flagTwo;

    @Bind(R.id.opponents_one)
    TextView opponentsOne;

    @Bind(R.id.team_one_score)
    TextView teamOneScore;

    @Bind(R.id.team_two_score)
    TextView teamTwoScore;

    @Bind(R.id.opponents_two)
    TextView opponentsTwo;

    @Bind(R.id.single_match)
    RelativeLayout matchView;

    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;

    @Bind(R.id.layout_game_frame)
    FrameLayout gameFrame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            tournId = getArguments().getString(TOURNID);
            matchId = getArguments().getString(GAMEID);
            //((MainActivity)getActivity()).getSupportActionBar().setTitle(gametitle);
        }
        listGames();
        // showChat();

    }

    public static GameFragment newInstance(String gameId, String tournId) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(GAMEID, gameId);
        args.putString(TOURNID, tournId);
        fragment.setArguments(args);
        return fragment;
    }

    // Load the drawer into the view.

//    public void showChat() {
//        //chatScreen.setVisibility(VISIBLE);
//        matchView.setVisibility(GONE);
//        loadingBar.setVisibility(GONE);
//        video.setBackgroundColor(Color.GREEN);
//        video.setVisibility(VISIBLE);

    //TODO commented chat
//        FragmentManager fragmentManager = getFragmentManager();
//        ChatFragment fragment;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = ChatFragment.newInstance("chat");
//        fragmentTransaction.add(R.id.layout_game_frame, fragment);
//        fragmentTransaction.commit();

//    }

    private void listGames() {
        final RestClient restClient = new RestClient();
        restClient.getApiService().getMatch(tournId, matchId).enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                if (response.isSuccessful()) {
                    try {
                        for (final Opponent participant : response.body().getOpponents()) {
                            customsLogos = false;
                            if (participant.getParticipant() != null) {
                                restClient.getApiService().getParticipants(tournId, participant.getParticipant().getId()).enqueue(new Callback<Participant>() {
                                    @Override
                                    public void onResponse(Call<Participant> call, Response<Participant> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getLogo() != null) {
                                                Logo logo = response.body().getLogo();
                                                customsLogos = true;
                                                String imageUrl = logo.getExtra_small_square().replace("\\", "");
                                                if (!firstLoaded) {
                                                    Glide.with(flagOne.getContext())
                                                            .load(imageUrl)
                                                            .into(flagOne);
                                                    firstLoaded = true;
                                                    flagOne.setScaleType(ImageView.ScaleType.FIT_XY);
                                                } else {
                                                    Glide.with(flagTwo.getContext())
                                                            .load(imageUrl)
                                                            .into(flagTwo);
                                                    flagTwo.setScaleType(ImageView.ScaleType.FIT_XY);

                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Participant> call, Throwable t) {

                                    }
                                });
                            }
                        }
                        int teamOne = response.body().getOpponents().get(0).getScore();
                        int teamTwo = response.body().getOpponents().get(1).getScore();
                        teamOneScore.setText(String.valueOf(teamOne));
                        teamTwoScore.setText(String.valueOf(teamTwo));

                        if (response.body().getOpponents().get(0).getParticipant() != null) {
                            if (response.body().getOpponents().get(0).getParticipant().getName() != null) {
                                opponentsOne.setText(response.body().getOpponents().get(0).getParticipant().getName());
                            } else {
                                opponentsOne.setText(R.string.tbd);
                            }
                        } else {
                            opponentsOne.setText(R.string.tbd);
                        }
                        if (response.body().getOpponents().get(1).getParticipant() != null) {
                            if (response.body().getOpponents().get(1).getParticipant().getName() != null) {
                                opponentsTwo.setText(response.body().getOpponents().get(1).getParticipant().getName());
                            } else {
                                opponentsTwo.setText(R.string.tbd);
                            }
                        } else {
                            opponentsTwo.setText(R.string.tbd);
                        }
                        if (!customsLogos) {
                            String countryOne;
                            String countryTwo;
                            int countryIdOne;
                            int countryIdTwo;
                            if (response.body().getOpponents().get(0).getParticipant() != null) {
                                if (response.body().getOpponents().get(0).getParticipant().getCountry() != null) {
                                    countryOne = response.body().getOpponents().get(0).getParticipant().getCountry().toLowerCase();
                                    countryIdOne = getResources().getIdentifier(countryOne, "drawable", getContext().getPackageName());
                                    flagOne.setImageResource(countryIdOne);
                                } else {
                                    setupFlag(response.body().getDiscipline(), flagOne, flagTwo);
                                }
                            } else {
                                setupFlag(response.body().getDiscipline(), flagOne, flagTwo);
                            }


                            if (response.body().getOpponents().get(1).getParticipant() != null) {
                                if (response.body().getOpponents().get(1).getParticipant().getCountry() != null) {
                                    countryTwo = response.body().getOpponents().get(1).getParticipant().getCountry().toLowerCase();
                                    countryIdTwo = getResources().getIdentifier(countryTwo, "drawable", getContext().getPackageName());
                                    flagTwo.setImageResource(countryIdTwo);
                                } else {
                                    setupFlag(response.body().getDiscipline(), flagOne, flagTwo);
                                }
                            } else {
                                setupFlag(response.body().getDiscipline(), flagOne, flagTwo);
                            }


                        }
                        if(response.body().getStatus().equals("completed")){
                            if (showWinners) {
                                if (teamOne > teamTwo) {
                                    opponentsOne.setTextColor(Color.GREEN);
                                    opponentsTwo.setTextColor(Color.RED);
                                } else if (teamTwo > teamOne) {
                                    opponentsOne.setTextColor(Color.RED);
                                    opponentsTwo.setTextColor(Color.GREEN);
                                } else {
                                    opponentsOne.setTextColor(Color.YELLOW);
                                    opponentsTwo.setTextColor(Color.YELLOW);
                                }
                            }
                        }
                        Match match = response.body();
                        loadingBar.setVisibility(GONE);

                        if (match.getStreams().size() > 0) {
                            String Url = match.getStreams().get(0).getUrl();
                            if (Url.contains("twitch")) {
                                Url += "/embed";
                            }
                            video.getSettings().setJavaScriptEnabled(true);
                            video.getSettings().setLoadWithOverviewMode(true);
                            video.setWebViewClient(new WebViewClient());
                            video.loadUrl(Url);
                            video.setVisibility(VISIBLE);
                        }
                        matchView.setVisibility(VISIBLE);

                    } catch (Exception e) {
                        loadingBar.setVisibility(GONE);
                        Log.d("_____________", Log.getStackTraceString(e));
                    }

                } else {
                    loadingBar.setVisibility(GONE);
                    Toast.makeText(getActivity(), "You done goofed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFlag(String id, ImageView teamOneFlag, ImageView teamTwoFlag) {
        int iconValue = getActivity().getResources().getIdentifier(id + "_icon", "drawable", getActivity().getPackageName());
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
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }
}




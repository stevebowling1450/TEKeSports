package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.teky.tekesports.Adapters.MatchAdapter;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.Model.Match;
import com.teky.tekesports.Model.Tournament;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchFragment extends Fragment {

    @Bind(R.id.match_list_view)
    RecyclerView matchList;

    @Bind(R.id.match_list_view_past)
    RecyclerView matchListPast;

    @Bind(R.id.match_list_view_future)
    RecyclerView matchListFuture;

//    @Bind(R.id.tourn_logo)
//    ImageView logoView;

    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;

    @Bind(R.id.tabHost)
    TabHost matchTabHost;

//    @Bind(R.id.detail_button)
//    Button detailButton;

    private MatchAdapter matchAdapter;
    private MatchAdapter matchAdapterPast;
    private MatchAdapter matchAdapterFuture;

    private static final String TOURNAMENTID = "tournamentId";
    private static final String DISCIPLINE = "discipline";
    private static final String GAMETITLE = "title";
    private String tournamentId;
    private String gameDiscipline;
    private String gametitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match_list_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            tournamentId = getArguments().getString(TOURNAMENTID);
            gameDiscipline = getArguments().getString(DISCIPLINE);
            gametitle = getArguments().getString(GAMETITLE);
            ((MainActivity) getActivity()).getToolbar().setTitle(gametitle);
        }
        ((MainActivity) getActivity()).getToolbar().setTitle(gametitle);

        Log.i("TITLE", tournamentId);
        Log.i("TITLE", gameDiscipline);
        Log.i("TITLE", gametitle);
//        int id = getResources().getIdentifier(gameDiscipline + "_logo", "drawable", getActivity().getPackageName());
//        logoView.setImageResource(id);

        //int id = getResources().getIdentifier(intent.getStringExtra("id") + "_logo", "drawable", getActivity().getPackageName());
        //logoView.setImageResource(id);


        matchAdapter = new MatchAdapter(new ArrayList<Match>(), getActivity(), getActivity().getSupportFragmentManager());
        matchAdapterPast = new MatchAdapter(new ArrayList<Match>(), getActivity(), getActivity().getSupportFragmentManager());
        matchAdapterFuture = new MatchAdapter(new ArrayList<Match>(), getActivity(), getActivity().getSupportFragmentManager());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerPast = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerFuture = new LinearLayoutManager(getActivity());

        matchList.setLayoutManager(linearLayoutManager);
        matchListPast.setLayoutManager(linearLayoutManagerPast);
        matchListFuture.setLayoutManager(linearLayoutManagerFuture);


        matchList.setAdapter(matchAdapter);
        matchListPast.setAdapter(matchAdapterPast);
        matchListFuture.setAdapter(matchAdapterFuture);

//        TabHost host = (TabHost) getActivity().findViewById(tabHost);
//        host.setup();

        matchTabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = matchTabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Past");
        matchTabHost.addTab(spec);

        //Tab 2
        spec = matchTabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ongoing");
        matchTabHost.addTab(spec);
//
//        //Tab 3
//        spec = matchTabHost.newTabSpec("Tab Three");
//        spec.setContent(R.id.tab3);
//        spec.setIndicator("Upcoming");
//        matchTabHost.addTab(spec);


        listMatches(matchAdapterPast, 1);
        listMatches(matchAdapter, 0);
        //listMatches(matchAdapterFuture, "schedule", "future");
    }

    public static MatchFragment newInstance(String tournamentId, String gameDiscipline, String gameTitle) {
        MatchFragment fragment = new MatchFragment();
        Bundle args = new Bundle();
        args.putString(TOURNAMENTID, tournamentId);
        args.putString(DISCIPLINE, gameDiscipline);
        args.putString(GAMETITLE, gameTitle);
        fragment.setArguments(args);
        return fragment;
    }

    private void listMatches(final MatchAdapter adapter, final int hasResult) {

        RestClient restClient = new RestClient();
        restClient.getApiService().getMatches(tournamentId, hasResult).enqueue(new Callback<Match[]>() {
            @Override
            public void onResponse(Call<Match[]> call, Response<Match[]> response) {
                if (response.isSuccessful()) {
                    ArrayList<Match> matches = new ArrayList<>(Arrays.asList(response.body()));

                    Iterator<Match> matchIterator = matches.iterator();
                    while (matchIterator.hasNext()) {
                        Match match = matchIterator.next();
                        if (match.getOpponents().get(1).getParticipant() == null && match.getOpponents().get(0).getParticipant() == null) {
                            matchIterator.remove();
                        } else if (match.getOpponents().get(0).getParticipant() != null && match.getOpponents().get(0).getParticipant() != null) {
                            if (match.getOpponents().get(0).getParticipant().getName() == null && match.getOpponents().get(1).getParticipant().getName() == null) {
                                matchIterator.remove();
                            }
                        }
                    }

                    adapter.matches = matches;
                }


                // Notify the adapter of the data change.
                adapter.notifyDataSetChanged();
                // Initialize and ArrayList of matches.
                // Hide the loading bar and show the match list.
                loadingBar.setVisibility(View.GONE);

                // Call the method that lists the match details.
                matchTabHost.setVisibility(View.VISIBLE);
                matchTabHost.setCurrentTab(1);
                listDetails();
            }


            @Override
            public void onFailure(Call<Match[]> call, Throwable t) {
                Log.v("****THROWABLE***", t.getMessage());
                t.printStackTrace();
                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String details;

    private void listDetails() {

        RestClient restClient = new RestClient();
        restClient.getApiService().getTournament(tournamentId).enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if (response.isSuccessful()) {
                    details = response.body().getDescription();
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                // Toast.makeText(MatchFragment.this, " No details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
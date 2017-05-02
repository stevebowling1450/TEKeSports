package com.teky.tekesports.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.teky.tekesports.Adapters.TournamentAdapter;
import com.teky.tekesports.Components.Constants;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.Model.Tournament;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;
import com.teky.tekesports.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.teky.tekesports.R.id.tabHost;
import static com.teky.tekesports.R.id.tourn_item_back;

/**
 * Created by jessemaynard on 11/30/16.
 * toornament-android.
 */

public class TournamentFragment extends Fragment {


    @Bind(R.id.tourn_list_view)
    RecyclerView tournList;

    @Bind(R.id.tourn_list_view_past)
    RecyclerView tournListPast;

    @Bind(R.id.tourn_list_view_setup)
    RecyclerView tournListSetup;

    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;

    @Bind(tabHost)
    TabHost tabhost;


    private static final String DISCLIPLINE = "discipline";
    private static final String GAMETITLE = "title";
    private String gameDiscipline;
    private String gameTitle = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tournament_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            gameDiscipline = getArguments().getString(DISCLIPLINE);
            gameTitle = getArguments().getString(GAMETITLE);
        }
        if (gameTitle != null) {
            ((MainActivity) getActivity()).getToolbar().setTitle(gameTitle);
        }

        TournamentAdapter tournAdapter = new TournamentAdapter(new ArrayList<Tournament>(), getActivity());
        TournamentAdapter tournAdapterPast = new TournamentAdapter(new ArrayList<Tournament>(), getActivity());
        TournamentAdapter tournAdapterSetup = new TournamentAdapter(new ArrayList<Tournament>(), getActivity());
        LinearLayoutManager linearLayoutManagerPast = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerSetup = new LinearLayoutManager(getActivity());
        tournListPast.setLayoutManager(linearLayoutManagerPast);
        tournListPast.setAdapter(tournAdapterPast);

        tournList.setLayoutManager(linearLayoutManager);
        tournList.setAdapter(tournAdapter);

        tournListSetup.setLayoutManager(linearLayoutManagerSetup);
        tournListSetup.setAdapter(tournAdapterSetup);

        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(getActivity(), tournList);
        RecyclerTouchListener onTouchListenerPast = new RecyclerTouchListener(getActivity(), tournListPast);
        RecyclerTouchListener onTouchListenerSetup = new RecyclerTouchListener(getActivity(), tournListSetup);

//        tournList.addOnItemTouchListener(onTouchListener);

        TabHost host = (TabHost) getActivity().findViewById(tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Past");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ongoing");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Upcoming");
        host.addTab(spec);

        HashMap<String, String> future = new HashMap<>();
        future.put("after_start", Utils.formattedDate());
        // future.put("before_start", Utils.formattedDate());


        HashMap<String, String> others = new HashMap<>();
        others.put("before_start", Utils.formattedDate());

        listTournaments(tournAdapterPast, others, "date_desc", "completed", onTouchListenerPast);
        listTournaments(tournAdapter, others, "date_desc", "running", onTouchListener);
        listTournaments(tournAdapterSetup, future, "date_asc", "setup", onTouchListenerSetup);

        tabhost.setCurrentTab(1);

        tournList.addOnItemTouchListener(onTouchListener);
        tournListPast.addOnItemTouchListener(onTouchListenerPast);
        tournListSetup.addOnItemTouchListener(onTouchListenerSetup);


    }

    public static TournamentFragment newInstance(String disclipline, String gameTitle) {
        TournamentFragment fragment = new TournamentFragment();
        Bundle args = new Bundle();
        args.putString(DISCLIPLINE, disclipline);
        args.putString(GAMETITLE, gameTitle);
        fragment.setArguments(args);
        return fragment;


    }

    // OnClick methods for games in the fragment_discipline_list_view.


    private void listTournaments(final TournamentAdapter adapter, HashMap<String, String> queryMap, final String sort, final String status, final RecyclerTouchListener listener) {
        final RestClient restClient = new RestClient();
        restClient.getApiService().getTournaments(queryMap, gameDiscipline, sort, status).enqueue(new Callback<Tournament[]>() {
            @Override
            public void onResponse(Call<Tournament[]> call, Response<Tournament[]> response) {
                if (response.isSuccessful()) {
                    adapter.tournaments = new ArrayList<>(Arrays.asList(response.body()));

                    adapter.notifyDataSetChanged();
                    loadingBar.setVisibility(View.GONE);tabhost.setVisibility(View.VISIBLE);
                    tournList.setVisibility(View.VISIBLE);
                    tournListPast.setVisibility(View.VISIBLE);
                    tournListSetup.setVisibility(View.VISIBLE);


                    listener.setIndependentViews(R.id.details_button).setClickable(new RecyclerTouchListener.OnRowClickListener() {
                        @Override
                        public void onRowClicked(int position) {
                            Fragment fragment;
                            // Open the MatchFragment with the information from the item clicked.
                            fragment = MatchFragment.newInstance(adapter.tournaments.get(position).getId(), adapter.tournaments.get(position).getDiscipline(), adapter.tournaments.get(position).getName());
                            ((MainActivity) getContext()).openNewView(fragment, Constants.MATCHFRAGMENT);
                        }

                        @Override
                        public void onIndependentViewClicked(int independentViewID, int position) {
                        }
                    });

//                        listener.setSwipeOptionViews(R.id.button_back).setViewsToFade(R.id.tourn_item).setSwipeable(R.id.tourn_item, R.id.tourn_item_back, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
//                            @Override
//                            public void onSwipeOptionClicked(int viewID, int position) {
//                                if (viewID == R.id.button_back) {
//                                    Toast.makeText(getActivity(), "Button Works!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });


                } else {
                    Toast.makeText(getActivity(), "Failed to List Tournaments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tournament[]> call, Throwable t) {
                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home_button).setVisible(true);
    }

}
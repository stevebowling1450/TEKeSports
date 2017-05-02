package com.teky.tekesports.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teky.tekesports.Model.Tournament;
import com.teky.tekesports.Network.RestClient;
import com.teky.tekesports.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jessemaynard on 12/1/16.
 * toornament-android.
 */

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentHolder> {

    public ArrayList<Tournament> tournaments;
    private final Context context;

    public TournamentAdapter(ArrayList<Tournament> tournaments, Context context) {
        this.tournaments = tournaments;
        this.context = context;
    }

    @Override
    public TournamentAdapter.TournamentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item_tournament, parent, false);

        return new TournamentHolder(inflateView);
    }


    @Override
    public void onBindViewHolder(TournamentAdapter.TournamentHolder holder, int position) {
        if (position < tournaments.size()) {
            Tournament tournament = tournaments.get(position);
            holder.bindTournament(tournament);

        }
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    class TournamentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tourn_name)
        TextView tournName;

        @Bind(R.id.tourn_date)
        TextView tournDate;

        @Bind(R.id.tourn_id)
        TextView tournId;

        @Bind(R.id.location_flag)
        ImageView locationFlag;

        @Bind(R.id.location_name)
        TextView locationName;

        @Bind(R.id.details_button)
        Button detailsButton;

        String id = "";
        String discipline;
        String name;
        String description;
        boolean isChecked = false;
        boolean isVisible = false;

        public TournamentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        // Adds the data to the view.
        public void bindTournament(final Tournament tournament) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE d, MMM yyyy ", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("EST"));

            id = tournament.getId();
            discipline = tournament.getDiscipline();
            name = tournament.getName();


            if (!isChecked) {
                RestClient restClient = new RestClient();
                restClient.getApiService().getTournament(id).enqueue(new Callback<Tournament>() {
                    @Override
                    public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getDescription() != null) {
                                    description = response.body().getDescription();
                                    detailsButton.setVisibility(View.VISIBLE);
                                    isVisible = true;

                                }
                            }
                        }
                        isChecked = true;

                    }

                    @Override
                    public void onFailure(Call<Tournament> call, Throwable t) {

                    }
                });
            }

            if (isVisible) {
                detailsButton.setVisibility(View.VISIBLE);
            }
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setIcon(R.mipmap.ic_launcher);
                    alert.setTitle("Details");
                    alert.setMessage(description);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            });


            // Get the date and set the name of the tournament.
            Date date = tournament.getDate_start();
            tournName.setText(tournament.getName());
            // Set the id of the tournament in a gone view for easy access later.
            tournId.setText(tournament.getId());

            // If the tournament is hosted online set the location to online otherwise set it
            // to the actual location.
            if (tournament.isOnline()) {
                locationName.setText(R.string.status_online);
            } else if (!tournament.isOnline() && tournament.getLocation() == null) {
                locationName.setText(R.string.status_online);
            } else {
                locationName.setText(tournament.getLocation().length() < 20 ? tournament.getLocation() : "Online");
            }
            try {
                String location = tournament.getCountry().toLowerCase();
                int locationId = context.getResources().getIdentifier(location, "drawable", context.getPackageName());
                locationFlag.setImageResource(locationId);
            } catch (NullPointerException e) {
                locationFlag.setImageResource(R.drawable.network_icon);
            }
            // If the tournament has a set date set the date field to it, otherwise, set the field
            // to TBD.
            try {
                tournDate.setText(formatter.format(date));
            } catch (NullPointerException e) {
                tournDate.setText(R.string.date_not_found);
            }
        }

    }
}


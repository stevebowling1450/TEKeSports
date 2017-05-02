package com.teky.tekesports.Components;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.Network.RestClient;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 11/29/16.
 * toornament-android.
 */

public class Constants {

    public static final String TWITTERFRAGMENT = "Twitter";
    public static final String POPULARFRAGMENT = "Popular";
    public static final String MATCHFRAGMENT = "Match";
    public static final String GENREFIRSTFRAGMENT = "GenreFirst";
    public static final String GAMEFRAGMENT = "Game";
    public static final String FRIENDLISTFRAGMENT = "Friend";
    public static final String FAVORITESFRAGMENT = "Favorites";
    public static final String TOURNAMENTFRAGMENT = "Tournament";
    public static final String CHATFRAGMENT = "Chat";

    public static final String API_KEY = "mb-73m0u0aiYGH7tPTqedj2-tTIydB_MmcUfs2bcHQY";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "7EP1RpdipUAheIJTQKwrn4yjy";
    public static final String TWITTER_SECRET = "Lw7xoCriaLPxf0WGBjyY0jsRh86FfH0AQsH1TAGCRbXsLbDUTT";

    public static final Integer PLAYER_USER = 1;
    public static final Integer PLAYER_SCRUMMASTER = 2;
    public static final Integer PLAYER_MODERATOR = 3;
    public static final Integer PLAYER_ADMIN = 4;
    public static final Integer PLAYER_OWNER = 5;

    public static final String DOTA2 = "dota2";
    public static final String LEAUGEOFLEGENDS = "leagueoflegends";
    public static final String COUNTERSTRIKE_GO = "counterstrike_go";
    public static final String HEARTHSTONE = "hearthstone";
    public static final String ROCKETLEAGUE = "rocketleague";
    public static final String SUPERSMASHBROS = "supersmashbros";
    public static final String COD_ADVANCED_WAREFARE = "cod_advanced_warfare";
    public static final String VAINGLORY = "vainglory";
    public static final String CLASH_ROYALE = "clash_royale";
    public static final String FIFA16 = "fifa16";

    public static List<Disciplines> MOBA = new LinkedList<>();
    public static List<Disciplines> RTS = new LinkedList<>();
    public static List<Disciplines> Fighting = new LinkedList<>();
    public static List<Disciplines> Sports = new LinkedList<>();
    public static List<Disciplines> FPS = new LinkedList<>();
    public static List<Disciplines> Other = new LinkedList<>();



    public static boolean getDefaultGames() {

        RestClient restclient = new RestClient();
        restclient.getApiService().getGames().enqueue(new Callback<Disciplines[]>() {
            @Override
            public void onResponse(Call<Disciplines[]> call, Response<Disciplines[]> response) {
                MOBA.clear();
                RTS.clear();
                Fighting.clear();
                Sports.clear();
                FPS.clear();
                Other.clear();

                List<Disciplines> games = Arrays.asList(response.body());
                for (Disciplines gameType : games) {
                    String discipline = gameType.getId();

                    switch (discipline) {
                        case "dota2":
                        case "leagueoflegends":
                        case "smite":
                        case "heroesofthestorm":
                        case "vainglory":
                            MOBA.add(gameType);
                            break;
                        case "starcraft2_lotv":
                        case "starcraft2_wol":
                        case "starcraft2_hots":
                        case "clash_royale":
                        case "ageofempires2":
                            RTS.add(gameType);
                            break;
                        case "supersmashbros":
                        case "supersmashbros_3ds_wiiu":
                        case "tekkentagtournament2":
                        case "mortalkombatx":
                        case "streetfighter4":
                            Fighting.add(gameType);
                            break;
                        case "fifa16":
                        case "rocketleague":
                        case "nba17":
                        case "madden17":
                        case "fifa17":
                            Sports.add(gameType);
                            break;
                        case "cod_advanced_warfare":
                        case "counterstrike_go":
                        case "overwatch":
                        case "halo5":
                        case "battlefield1":
                            FPS.add(gameType);
                            break;
                        case "hearthstone":
                        case "wow":
                        case "worldofwarplanes":
                        case "minecraft":
                        case "mariokart8":
                            Other.add(gameType);

                    }

                }

            }

            @Override
            public void onFailure(Call<Disciplines[]> call, Throwable t) {

            }
        });

        return true;
    }

    public static void getAllGames(final String type) {

        FirebaseDatabase.getInstance().getReference().child("gametypes").child(type).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Disciplines>> t = new GenericTypeIndicator<List<Disciplines>>() {
                };
                List<Disciplines> games = dataSnapshot.getValue(t);
                switch (type) {
                    case "MOBA":
                        MOBA = games;
                        break;
                    case "RTS":
                        RTS = games;
                        break;
                    case "Fighting":
                        Fighting = games;
                        break;
                    case "Sports":
                        Sports = games;
                        break;
                    case "FPS":
                        FPS = games;
                        break;
                    case "Other":
                        Other = games;
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

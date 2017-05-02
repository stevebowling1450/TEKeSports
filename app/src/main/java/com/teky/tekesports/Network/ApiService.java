package com.teky.tekesports.Network;

import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.Model.Match;
import com.teky.tekesports.Model.Participant;
import com.teky.tekesports.Model.Tournament;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lennyhicks on 11/29/16.
 * toornament-android.
 */

public interface ApiService {

    //Get all Tournaments
    @GET("v1/tournaments" + "?start_date!=null")
    Call<Tournament[]> getTournaments(@QueryMap Map<String, String> options, @Query("discipline") String discipline, @Query("sort") String sort, @Query("status") String status);

    //Get single Tournament
    @GET("v1/tournaments/{id}")
    Call<Tournament> getTournament(@Path("id") String id);

    //Get all games
    @GET("v1/disciplines")
    Call<Disciplines[]> getGames();

    //Get single Game
//    @GET("v1//disciplines/{id}")
//    Call<Disciplines> getGame(@Path("id") String id);

    //Get Matches
    @GET("v1/tournaments/{tournament_id}/matches")
    Call<Match[]> getMatches(@Path("tournament_id") String tid, @Query("has_result") Integer hasResult);

    //Get Matches
    @GET("v1/tournaments/{tournament_id}/matches/{id}")
    Call<Match> getMatch(@Path("tournament_id") String tid, @Path("id") String id);

    //Get Match Result
//    @GET("v1/tournaments/{tournament_id}/matches/{id}/result")
//    Call<Result> getResult(@Path("tournament_id") String tid, @Path("id") String id);
//
//    //Get Match by Game
//    @GET("v1/disciplines/{discipline_id}/matches")
//    Call<Match[]> getMatchByGame(@Path("discipline_id") String did);
//
//    //Get Games for a tournament
//    @GET("v1/tournaments/{tournament_id}/matches/{match_id}/games")
//    Call<Game[]> getTournamentGames(@Path("tournament_id") String tid, @Path("match_id") String mid);
//
//    //Get Single Game for a tournament
//    @GET("v1/tournaments/{tournament_id}/matches/{match_id}/games/{number}")
//    Call<Game> getTournamentGame(@Path("tournament_id") String tid, @Path("match_id") String mid, @Path("number") Integer number);
//
//    //Get Result for a Single Game for a tournament
//    @GET("v1/tournaments/{tournament_id}/matches/{match_id}/games/{number}/result")
//    Call<Result> getTournamentGameResult(@Path("tournament_id") String tid, @Path("match_id") String mid, @Path("number") Integer number);
//
//    //Get Participants for a tournament
//    @GET("v1/tournaments/{tournament_id}/participants")
//    Call<Participant[]> getParticipants(@Path("tournament_id") String tid);

    //Get Single Participant for a tournament
    @GET("v1/tournaments/{tournament_id}/participants/{id}")
    Call<Participant> getParticipants(@Path("tournament_id") String tid, @Path("id") String id);

    //Get Schedules for a tournament
//    @GET("v1/tournaments/{tournament_id}/schedules")
//    Call<Schedules> getSchedules(@Path("tournament_id") String tid);

}

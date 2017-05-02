package com.teky.tekesports.Utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teky.tekesports.Model.GameTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by lennyhicks on 12/12/16.
 * toornament-android.
 */

public class GameTypeList {

    public static List<String> types = new ArrayList<>();

    public static void addToList(final String list, final GameTypes currentGame, final GameTypes selection) {
        FirebaseDatabase.getInstance().getReference().child("gametypes").child(list).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                removeFromAllList(list, currentGame);

                List<GameTypes> allGames = new ArrayList<>();
                for (DataSnapshot games : dataSnapshot.getChildren()) {
                    GameTypes gamess = games.getValue(GameTypes.class);
                    allGames.add(gamess);
                }

                boolean notAdded = true;
                for (GameTypes games : allGames) {
                    if (currentGame.getFullName().equals(games.getFullName())) {
                        notAdded = false;
                    }

                }
                if (notAdded) {


                    allGames.add(currentGame);

                    FirebaseDatabase.getInstance()
                            .getReference().child("gametypes").child(selection.getGameType()).setValue(allGames);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void remomveFromList(final String list, final GameTypes currentGame) {
        FirebaseDatabase.getInstance().getReference().child("gametypes").child(list).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<GameTypes> allGames = new ArrayList<>();
                for (DataSnapshot games : dataSnapshot.getChildren()) {
                    GameTypes gamess = games.getValue(GameTypes.class);

                    allGames.add(gamess);
                }

                boolean added = false;

                ListIterator<GameTypes> gameTypesListIterator;
                gameTypesListIterator = allGames.listIterator();
                while (gameTypesListIterator.hasNext()) {
                    GameTypes games = gameTypesListIterator.next();
                    if (games.getFullName().equals(currentGame.getFullName())) {
                        gameTypesListIterator.remove();
                        added = true;
                    }

                }


                if (added) {
                    FirebaseDatabase.getInstance()
                            .getReference().child("gametypes").child(list).setValue(allGames);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void removeFromAllList(final String currentList, final GameTypes currentGame) {

        FirebaseDatabase.getInstance().getReference().child("gametypes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot games : dataSnapshot.getChildren()) {
                    if (!games.getKey().equals(currentList)) {
                        remomveFromList(games.getKey(), currentGame);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public static List<String> getTypes() {
//        FirebaseDatabase.getInstance().getReference().child("gametypes").addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<String> type = new ArrayList<>();
//                for (DataSnapshot games : dataSnapshot.getChildren()) {
//                    type.add(games.getKey());
//                }
//                setTypes(type);
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        return types;
//    }


    public static List<String> getAll() {
        FirebaseDatabase.getInstance().getReference().child("gametypes").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> type = new ArrayList<>();
                for (DataSnapshot games : dataSnapshot.getChildren()) {
                    type.add(games.getKey());


                }
                setTypes(type);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("TYPES", types.toString());
        return types;
    }

    public static void setTypes(List<String> types) {
        GameTypeList.types = types;
    }

}

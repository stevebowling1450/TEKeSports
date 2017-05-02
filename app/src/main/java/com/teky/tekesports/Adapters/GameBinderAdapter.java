package com.teky.tekesports.Adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.teky.tekesports.BR;
import com.teky.tekesports.Components.Constants;
import com.teky.tekesports.Dialog.GameSelectionDialog;
import com.teky.tekesports.Fragments.TournamentFragment;
import com.teky.tekesports.MainActivity;
import com.teky.tekesports.Model.Disciplines;
import com.teky.tekesports.databinding.ItemGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static com.teky.tekesports.Utils.CurrentUser.currUser;
import static com.teky.tekesports.Utils.CurrentUser.saveUser;

/**
 * Created by lennyhicks on 12/7/16.
 * toornament-android.
 */

public class GameBinderAdapter extends RecyclerView.Adapter<GameBinderAdapter.BindingHolder> implements Filterable {
    private final FragmentManager fragmentManager;

    private final List<Disciplines> games;
    private final List<Disciplines> filteredGames = new ArrayList<>();
    private Filter gameFilter;
    private final Context context;

    public boolean favoriteFiltered = false;


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        imageUrl = "https://www.toornament.com/disciplines/" + imageUrl + "/img/icon-48x48-medium.png";
        Glide.with(view.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    @BindingAdapter({"bind:setText"})
    public static void loadText(TextView text, String gameCopyright) {
        text.setText(String.format("© %s", gameCopyright));
    }

    @BindingAdapter({"bind:isFaved"})
    public static void checkFave(final CheckBox checkBox, final String id) {
        checkBox.setChecked(false);
        ListIterator<Disciplines> favIterator;
        favIterator = currUser.getFavorites().listIterator();
        while (favIterator.hasNext()) {
            Disciplines fav = favIterator.next();
            if (fav.getId().equals(id)) {
                checkBox.setChecked(true);
                break;
            }
        }
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private final ItemGameBinding binding;
        private final Context context;
        private ArrayList<Disciplines> updatedFavs;

        public BindingHolder(final View rowView, final Context context, final FragmentManager fragmentManager) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
            this.context = context;
            binding.gameFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatedFavs = currUser.getFavorites();
                    if (binding.gameFav.isChecked()) {
                        boolean isFound = false;
                        for (Disciplines currentGame : currUser.getFavorites()) {
                            if (currentGame.getId().equals(binding.getGame().getId())) {
                                isFound = true;
                                break;
                            }

                        }
                        if (!isFound) {
                            updatedFavs.add(binding.getGame());
                        }
                    } else if (!binding.gameFav.isChecked()) {
                        for (Disciplines currentGame : currUser.getFavorites()) {
                            if (currentGame.getId().equals(binding.getGame().getId())) {
                                updatedFavs.remove(currentGame);
                                break;
                            }

                        }
                    }
                    currUser.setFavorites(updatedFavs);
                    saveUser(currUser);
                }
            });


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveUser(currUser);
                    openDiscipline(binding.getGame().getId());

                }
            });

            rowView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    GameSelectionDialog gameSelectionDialog = new GameSelectionDialog();
                    gameSelectionDialog.setupView(binding.getGame().getId(), binding.getGame().getFullname());

                    gameSelectionDialog.show(fragmentManager, "Set up Games");
                    return false;
                }
            });
        }

        private void openDiscipline(String id) {
            Fragment fragment = TournamentFragment.newInstance(id, binding.getGame().getName());
            ((MainActivity) context).openNewView(fragment, Constants.TOURNAMENTFRAGMENT);
        }

        private ViewDataBinding getBinding() {
            return binding;
        }


    }

    public GameBinderAdapter(List<Disciplines> recyclerUsers, Context context, FragmentManager fragmentManager) {
        this.games = recyclerUsers;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGameBinding binding = ItemGameBinding.inflate(inflater, parent, false);
        return new BindingHolder(binding.getRoot(), context, fragmentManager);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        if (position < filteredGames.size()) {
            Disciplines book = filteredGames.get(position);
            holder.getBinding().setVariable(BR.game, book);
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return filteredGames.size();
    }

    @Override
    public Filter getFilter() {
        if (gameFilter == null) {
            gameFilter = new UserFilter(this, games);
        }
        return gameFilter;
    }

    private class UserFilter extends Filter {

        private final GameBinderAdapter adapter;
        private final List<Disciplines> filteredList;
        private final List<Disciplines> orignalList;

        private UserFilter(GameBinderAdapter adapter, List<Disciplines> originalList) {
            super();
            this.adapter = adapter;
            this.orignalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            List<Disciplines> filtered;
            Set<Disciplines> set = new HashSet<>();
            if (favoriteFiltered) {
                if(currUser.getFavorites() != null) {
                    filtered = currUser.getFavorites();
                } else {
                    filtered = new ArrayList<>();
                }
            } else {
                filtered = orignalList;
            }

            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                set.addAll(filtered);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (Disciplines game : filtered) {
                    if (game.getName().toLowerCase().startsWith(filterPattern)) {
                        set.add(game);
                    }
                }
            }

            results.values = set;
            results.count = set.size();
            return results;
        }

        //TODO add copyrights to screen
        @Override
        protected void publishResults(CharSequence constraint, final FilterResults results) {
            filteredGames.clear();
            if(results.values != null) {
                if (results.values.toString().length() > 0) {
                    filteredGames.addAll((Set<Disciplines>) results.values);
                }
                Collections.sort(filteredGames, new Comparator<Disciplines>() {
                    @Override
                    public int compare(Disciplines disciplines, Disciplines t1) {
                        return disciplines.getName().compareTo(t1.getName());
                    }
                });

                adapter.notifyDataSetChanged();
            }
        }
    }
}
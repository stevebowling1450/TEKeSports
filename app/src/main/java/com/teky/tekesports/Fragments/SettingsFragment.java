package com.teky.tekesports.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teky.tekesports.MainActivity;
import com.teky.tekesports.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
//import de.cketti.mailto.EmailIntentBuilder;

/**
 * Created by jessemaynard on 12/19/16.
 */

public class SettingsFragment extends Fragment {

    public static String TITLE = "title";
    public String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_view, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
        }
        ((MainActivity) getActivity()).getToolbar().setTitle(title);

    }

    @OnClick(R.id.about_button)
    public void aboutButtonTapped(){
        Fragment fragment;
        fragment = new AboutFragment();
        ((MainActivity)getActivity()).openNewView(fragment, "About");
    }

//    @OnClick(R.id.feedback)
//    public void feedBackTapped() {
//        Intent emailIntent = EmailIntentBuilder.from(getActivity())
//                .to("Rodney.Tressler@Interapthq.com")
//                .subject("TekEsports Feedback")
//                .build();
//        startActivity(emailIntent);
//    }
}

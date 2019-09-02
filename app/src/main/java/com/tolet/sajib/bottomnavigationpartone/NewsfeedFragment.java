package com.tolet.sajib.bottomnavigationpartone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsfeedFragment extends Fragment {


    public NewsfeedFragment() {
        // Required empty public constructor
    }
    private static Fragment fragment;

    public static Fragment getFragment() {
        if (fragment == null) {
            fragment = new NewsfeedFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_newsfeed, container, false);

        return view;
    }

}

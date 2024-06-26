package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.activities.ScoutMatchActivity;

public class PostMatchScoutingFragment extends ScoutingFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_post_match, container, false);
        view.findViewById(R.id.finish).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.finish) {
            ((ScoutMatchActivity) getActivity()).finishScouting();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String mkey = ((ScoutMatchActivity) getActivity()).matchKey;
        int start = 0;
        for (int i = mkey.length() - 1; i >= 0; i--) {
            if (mkey.charAt(i) == 'm') {
                start = i;
                break;
            }
        }

        ScoutingNoteFragment f = ScoutingNoteFragment.newInstance(((ScoutMatchActivity) getActivity()).teamKey, mkey.substring(start));
        getFragmentManager().beginTransaction().replace(R.id.notes_container, f, "notes").commit();
    }
}

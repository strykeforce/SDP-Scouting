package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingSpinnerView;

import java.io.IOException;

public class TeleopScoutingFragment extends ScoutingFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_teleop, container, false);
        try {
            setSpinner(view);
        } catch (IOException | CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading team numbers. Check LogCat.", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public void setSpinner(View view) throws IOException, CouchbaseLiteException {
        ScoutingSpinnerView spinner = (ScoutingSpinnerView) view.findViewById(R.id.HumanSpinner);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1").contains("red")) {
            spinner.setSelectionBasedOnText((Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[0]).toString().substring(3));
            spinner.setSelectionBasedOnText((Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[1]).toString().substring(3));
            spinner.setSelectionBasedOnText((Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[2]).toString().substring(3));
            spinner.setSelectionBasedOnText("-");
        } else {
            spinner.setSelectionBasedOnText((Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[0]).toString().substring(3));
            spinner.setSelectionBasedOnText((Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[1]).toString().substring(3));
            spinner.setSelectionBasedOnText((Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getActivity()).getMatchFromKey(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("currMatchKey", "")))[2]).toString().substring(3));
            spinner.setSelectionBasedOnText("-");
        }
    }
}

package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingButtonView;

public class AutonomousScoutingFragment extends ScoutingFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_autonomous, container, false);
        String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");
        if (assignedTeamType.contains("red")) {
            System.out.println("Team is red");
            view.findViewById(R.id.redautonmap).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton5).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton6).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton7).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton8).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton9).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redbutton10).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redlabel).setVisibility(View.VISIBLE);
        } else {
            System.out.println("Team is blue");
            view.findViewById(R.id.blueautonmap).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton5).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton6).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton7).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton8).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton9).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluebutton10).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bluelabel).setVisibility(View.VISIBLE);
        }
        return view;
    }
}

package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingButtonView;

import java.util.ArrayList;

public class AutonomousScoutingFragment extends ScoutingFragment {
    private Handler handler = new Handler();
    private TextView preview;
    private String color;
    private int oneClicks;
    private int twoClicks;
    private int threeClicks;
    private int fourClicks;
    private int fiveClicks;
    private int sixClicks;
    private int sevenClicks;
    private int eightClicks;
    private int nineClicks;
    private int tenClicks;

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            if (color == "Red") {
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks().size() != oneClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note One ");
                    oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks().size() != twoClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Two ");
                    twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks().size() != threeClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Three ");
                    threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks().size() != fourClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Four ");
                    fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks().size() != fiveClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Five ");
                    fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks().size() != sixClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note One ");
                    sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks().size() != sevenClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note Two ");
                    sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks().size() != eightClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note Three ");
                    eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks().size() != nineClicks) {
                    preview.setText(preview.getText() + "\n--> Scored in Speaker ");
                    nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks().size() != tenClicks) {
                    preview.setText(preview.getText() + "\n--> Scored in Amp ");
                    tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks().size();
                }
            } else {
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks().size() != oneClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note One ");
                    oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks().size() != twoClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Two ");
                    twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks().size() != threeClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Three ");
                    threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks().size() != fourClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Four ");
                    fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks().size() != fiveClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Shared Note Five ");
                    fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks().size() != sixClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note One ");
                    sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks().size() != sevenClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note Two ");
                    sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks().size() != eightClicks) {
                    preview.setText(preview.getText() + "\n--> Collected Red Note Three ");
                    eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks().size() != nineClicks) {
                    preview.setText(preview.getText() + "\n--> Scored in Speaker ");
                    nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks().size();
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks().size() != tenClicks) {
                    preview.setText(preview.getText() + "\n--> Scored in Amp ");
                    tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks().size();
                }
            }

            handler.postDelayed(this, 500);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_autonomous, container, false);
        String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");
        preview = (TextView) view.findViewById(R.id.AutonPreview);
        if (assignedTeamType.contains("red")) {
            System.out.println("Team is red");
            color = "Red";
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

            oneClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton1)).getClicks().size();
            twoClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton2)).getClicks().size();
            threeClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton3)).getClicks().size();
            fourClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton4)).getClicks().size();
            fiveClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton5)).getClicks().size();
            sixClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton6)).getClicks().size();
            sevenClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton7)).getClicks().size();
            eightClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton8)).getClicks().size();
            nineClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton9)).getClicks().size();
            tenClicks = ((ScoutingButtonView) view.findViewById(R.id.redbutton10)).getClicks().size();
        } else {
            System.out.println("Team is blue");
            color = "Blue";
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

            oneClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton1)).getClicks().size();
            twoClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton2)).getClicks().size();
            threeClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton3)).getClicks().size();
            fourClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton4)).getClicks().size();
            fiveClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton5)).getClicks().size();
            sixClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton6)).getClicks().size();
            sevenClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton7)).getClicks().size();
            eightClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton8)).getClicks().size();
            nineClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton9)).getClicks().size();
            tenClicks = ((ScoutingButtonView) view.findViewById(R.id.bluebutton10)).getClicks().size();
        }
        handler.post(update);
        return view;
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacks(update);
        super.onDestroyView();
    }
}

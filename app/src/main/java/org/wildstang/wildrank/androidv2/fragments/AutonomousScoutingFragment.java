package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ArrayList<String> pressOrder;
    private ArrayList<Integer> stringLength;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_autonomous, container, false);
        String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");
        preview = (TextView) view.findViewById(R.id.AutonPreview);
        if (assignedTeamType.contains("red")) {
            color = "Red";
            view.findViewById(R.id.redautonmap).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redposbutton1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redposbutton2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redposbutton3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.redposlabel).setVisibility(View.VISIBLE);
        } else {
            color = "Blue";
            view.findViewById(R.id.blueautonmap).setVisibility(View.VISIBLE);
            view.findViewById(R.id.blueposbutton1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.blueposbutton2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.blueposbutton3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.blueposlabel).setVisibility(View.VISIBLE);
        }
        pressOrder = new ArrayList<>();
        stringLength = new ArrayList<>();
        view.findViewById(R.id.undo_button).setOnClickListener(v -> undo());
        handler.post(check);
        return view;
    }

    private Runnable check = new Runnable() {
        @Override
        public void run() {
            if (((ScoutingButtonView) getView().findViewById(R.id.redposbutton1)).getClicks().size() != 0 || ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton1)).getClicks().size() != 0) {
                posSelected("P1");
            } else if (((ScoutingButtonView) getView().findViewById(R.id.redposbutton2)).getClicks().size() != 0 || ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton2)).getClicks().size() != 0) {
                posSelected("P2");
            } else if (((ScoutingButtonView) getView().findViewById(R.id.redposbutton3)).getClicks().size() != 0 || ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton3)).getClicks().size() != 0) {
                posSelected("P3");
            } else handler.postDelayed(this, 100);
        }
    };

    private void posSelected(String pos) {
        if (pos.equals("P3")) {
            preview.setText(preview.getText() + "\nStarted on Source Side ");
            pressOrder.add(pos);
            stringLength.add(24);
        } else if (pos.equals("P1")) {
            preview.setText(preview.getText() + "\nStarted on Amp Side ");
            pressOrder.add(pos);
            stringLength.add(21);
        } else if (pos.equals("P2")) {
            preview.setText(preview.getText() + "\nStarted in Middle ");
            pressOrder.add(pos);
            stringLength.add(19);
        }

        if (color == "Red") {
            getView().findViewById(R.id.redposbutton1).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.redposbutton2).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.redposbutton3).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.redposlabel).setVisibility(View.INVISIBLE);

            getView().findViewById(R.id.redbutton1).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton2).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton3).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton4).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton5).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton6).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton7).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton8).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton9).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redbutton10).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redSpeakerMake).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.redSpeakerMiss).setVisibility(View.VISIBLE);

            oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks().size();
            twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks().size();
            threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks().size();
            fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks().size();
            fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks().size();
            sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks().size();
            sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks().size();
            eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks().size();
            nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks().size();
            tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks().size();
        } else if (color == "Blue") {
            getView().findViewById(R.id.blueposbutton1).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.blueposbutton2).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.blueposbutton3).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.blueposlabel).setVisibility(View.INVISIBLE);

            getView().findViewById(R.id.bluebutton1).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton2).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton3).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton4).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton5).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton6).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton7).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton8).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton9).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bluebutton10).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.blueSpeakerMake).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.blueSpeakerMiss).setVisibility(View.VISIBLE);

            oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks().size();
            twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks().size();
            threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks().size();
            fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks().size();
            fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks().size();
            sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks().size();
            sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks().size();
            eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks().size();
            nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks().size();
            tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks().size();
        }

        handler.post(update);
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            if (color == "Red") {
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks().size() != oneClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note One ");
                    oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks().size();
                    pressOrder.add("R1");
                    stringLength.add(21);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks().size() != twoClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Two ");
                    twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks().size();
                    pressOrder.add("R2");
                    stringLength.add(21);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks().size() != threeClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Three ");
                    threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks().size();
                    pressOrder.add("R3");
                    stringLength.add(23);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks().size() != fourClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Four ");
                    fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks().size();
                    pressOrder.add("R4");
                    stringLength.add(22);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks().size() != fiveClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Five ");
                    fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks().size();
                    pressOrder.add("R5");
                    stringLength.add(22);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks().size() != sixClicks) {
                    preview.setText(preview.getText() + "\n--> Red Note One ");
                    sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks().size();
                    pressOrder.add("R6");
                    stringLength.add(18);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks().size() != sevenClicks) {
                    preview.setText(preview.getText() + "\n--> Red Note Two ");
                    sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks().size();
                    pressOrder.add("R7");
                    stringLength.add(18);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks().size() != eightClicks) {
                    preview.setText(preview.getText() + "\n--> Red Note Three ");
                    eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks().size();
                    pressOrder.add("R8");
                    stringLength.add(20);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks().size() != nineClicks) {
                    preview.setText(preview.getText() + "\n--> Scored Speaker ");
                    nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks().size();
                    pressOrder.add("R9");
                    stringLength.add(20);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks().size() != tenClicks) {
                    preview.setText(preview.getText() + "\n--> Missed Speaker ");
                    tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks().size();
                    pressOrder.add("R10");
                    stringLength.add(20);
                }
            } else {
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks().size() != oneClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note One ");
                    oneClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks().size();
                    pressOrder.add("B1");
                    stringLength.add(21);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks().size() != twoClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Two ");
                    twoClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks().size();
                    pressOrder.add("B2");
                    stringLength.add(21);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks().size() != threeClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Three ");
                    threeClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks().size();
                    pressOrder.add("B3");
                    stringLength.add(23);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks().size() != fourClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Four ");
                    fourClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks().size();
                    pressOrder.add("B4");
                    stringLength.add(22);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks().size() != fiveClicks) {
                    preview.setText(preview.getText() + "\n--> Shared Note Five ");
                    fiveClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks().size();
                    pressOrder.add("B5");
                    stringLength.add(22);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks().size() != sixClicks) {
                    preview.setText(preview.getText() + "\n--> Blue Note One ");
                    sixClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks().size();
                    pressOrder.add("B6");
                    stringLength.add(19);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks().size() != sevenClicks) {
                    preview.setText(preview.getText() + "\n--> Blue Note Two ");
                    sevenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks().size();
                    pressOrder.add("B7");
                    stringLength.add(19);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks().size() != eightClicks) {
                    preview.setText(preview.getText() + "\n--> Blue Note Three ");
                    eightClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks().size();
                    pressOrder.add("B8");
                    stringLength.add(21);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks().size() != nineClicks) {
                    preview.setText(preview.getText() + "\n--> Scored Speaker ");
                    nineClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks().size();
                    pressOrder.add("B9");
                    stringLength.add(20);
                }
                if (((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks().size() != tenClicks) {
                    preview.setText(preview.getText() + "\n--> Missed Speaker ");
                    tenClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks().size();
                    pressOrder.add("B10");
                    stringLength.add(20);
                }
            }

            handler.postDelayed(this, 100);
        }
    };

    public void undo() {
        if (pressOrder.size() == 0) {
            return;
        }
        if (pressOrder.get(pressOrder.size() - 1).equals("R1")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton1)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            oneClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R2")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton2)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            twoClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R3")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton3)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            threeClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R4")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton4)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            fourClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R5")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton5)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            fiveClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R6")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton6)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            sixClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R7")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton7)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            sevenClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R8")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton8)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            eightClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R9")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton9)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            nineClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("R10")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.redbutton10)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            tenClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B1")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton1)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            oneClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B2")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton2)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            twoClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B3")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton3)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            threeClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B4")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton4)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            fourClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B5")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton5)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            fiveClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B6")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton6)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            sixClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B7")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton7)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            sevenClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B8")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton8)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            eightClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B9")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton9)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            nineClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).equals("B10")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = ((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).getClicks();
            newClicks.remove(newClicks.size() - 1);
            ((ScoutingButtonView) getView().findViewById(R.id.bluebutton10)).setClicks(newClicks);
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);
            tenClicks--;
            handler.post(update);
        } else if (pressOrder.get(pressOrder.size() - 1).substring(0, 1).equals("P")) {
            handler.removeCallbacks(update);
            ArrayList<Long> newClicks = new ArrayList<>();
            System.out.println("\nSize of newclicks: " + newClicks.size());
            ((ScoutingButtonView) getView().findViewById(R.id.redposbutton1)).resetClicks();
            ((ScoutingButtonView) getView().findViewById(R.id.redposbutton2)).resetClicks();
            ((ScoutingButtonView) getView().findViewById(R.id.redposbutton3)).resetClicks();
            ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton1)).resetClicks();
            ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton2)).resetClicks();
            ((ScoutingButtonView) getView().findViewById(R.id.blueposbutton3)).resetClicks();
            pressOrder.remove(pressOrder.size() - 1);
            preview.setText(((String) (preview.getText())).substring(0, preview.getText().length() - stringLength.get(stringLength.size() - 1)));
            stringLength.remove(stringLength.size() - 1);

            if (color == "Red") {
                getView().findViewById(R.id.redbutton1).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton2).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton3).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton4).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton5).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton6).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton7).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton8).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton9).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redbutton10).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redSpeakerMake).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.redSpeakerMiss).setVisibility(View.INVISIBLE);

                getView().findViewById(R.id.redposbutton1).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.redposbutton2).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.redposbutton3).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.redposlabel).setVisibility(View.VISIBLE);
            } else if (color == "Blue") {
                getView().findViewById(R.id.bluebutton1).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton2).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton3).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton4).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton5).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton6).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton7).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton8).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton9).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.bluebutton10).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.blueSpeakerMake).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.blueSpeakerMiss).setVisibility(View.INVISIBLE);

                getView().findViewById(R.id.blueposbutton1).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.blueposbutton2).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.blueposbutton3).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.blueposlabel).setVisibility(View.VISIBLE);
            }

            handler.post(check);
        }
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacks(check);
        handler.removeCallbacks(update);
        super.onDestroyView();
    }
}

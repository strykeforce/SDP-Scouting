package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.activities.ScoutPitActivity;

public class PitScoutingFragment extends ScoutingFragment implements View.OnClickListener {

    Button Camera_Button;
    int teamKey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_pit, container, false);
        view.findViewById(R.id.finish).setOnClickListener(this);

        teamKey = Utilities.teamNumberFromTeamKey(((ScoutPitActivity) getActivity()).teamKey);
        Camera_Button = (Button) view.findViewById(R.id.CameraButton);
        Camera_Button.setOnClickListener(this);
        
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.finish) {
            ((ScoutPitActivity) getActivity()).finishScouting();
        } else if (id== R.id.CameraButton) {
            System.out.println("Launching Camera");
            ScoutingCamera.launchCamera(getActivity(), teamKey);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ScoutingNoteFragment f = ScoutingNoteFragment.newInstance(((ScoutPitActivity) getActivity()).teamKey, "Pit Scouting");
        getFragmentManager().beginTransaction().replace(R.id.notes_container, f, "notes").commit();
    }
}

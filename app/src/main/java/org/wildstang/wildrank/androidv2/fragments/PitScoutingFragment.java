package org.wildstang.wildrank.androidv2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;



import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.activities.ScoutPitActivity;

public class PitScoutingFragment extends ScoutingFragment implements View.OnClickListener {

    Button Camera_Button;
    ImageView imageView;
    int Teamkey = 2767;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_pit, container, false);
        view.findViewById(R.id.finish).setOnClickListener(this);

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
            ScoutingCamera.launchCamera(getActivity(),Teamkey);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ScoutingCamera.isCameraResult(requestCode) && resultCode == getActivity().RESULT_OK) {
            System.out.println("IT ACTUALLY WORKEDDDDDDDDDDD");
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        ScoutingNoteFragment f = ScoutingNoteFragment.newInstance(((ScoutPitActivity) getActivity()).teamKey);
        getFragmentManager().beginTransaction().replace(R.id.notes_container, f, "notes").commit();
    }
}

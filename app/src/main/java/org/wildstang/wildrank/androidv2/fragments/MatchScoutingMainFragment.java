package org.wildstang.wildrank.androidv2.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.activities.ScoutMatchActivity;
import org.wildstang.wildrank.androidv2.adapters.MatchListAdapter;
import org.wildstang.wildrank.androidv2.adapters.TeamSummariesFragmentPagerAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.squareup.picasso.Picasso;

public class MatchScoutingMainFragment extends Fragment implements View.OnClickListener {

    private ListView list;

    private TextView scoutingTeam;
    private Button beginScouting;
    private TextView matchNumber;

    private static String selectedTeamToScout;
    private static String selectedMatchKey;

    private int currPosition;

    private MatchListAdapter adapter;

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public MatchScoutingMainFragment() {
        // Required empty public constructor
    }

    public static String getSelectedTeamToScout() {
        return selectedTeamToScout;
    }

    public static String getSelectedMatchKey() {
        return selectedMatchKey;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (sharedPreferences, key) -> {
            if (key == null) {
                return;
            }
            if (key.equals("assignedTeam")) {
                if (MatchScoutingMainFragment.this.isAdded()) {
                    // Requery the list to update which matches are scouted or not
                    try {
                        Log.d("wildrank", "Requerying match list!");

                        runQuery();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error querying the match list. Check logcat!", Toast.LENGTH_LONG).show();
                    }

                    updateAssignedTeam();

                    // Update the selected match view if it exists
                    if (selectedMatchKey != null) {
                        try {
                            Log.d("wildrank", "Requerying match details!");
                            onMatchSelected(DatabaseManager.getInstance(MatchScoutingMainFragment.this.getActivity()).getMatchFromKey(selectedMatchKey));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("wildrank", "You idiot, it's null!");

                    }
                } else {
                    Log.d("wildrank", "Fragment not added!");
                }
            }

        };

        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_scouting_main, container, false);

        list = (ListView) view.findViewById(R.id.match_list);
        list.setOnItemClickListener((parent, view1, position, id) -> {
            currPosition = position;
            QueryRow row = (QueryRow) parent.getItemAtPosition(position);
            onMatchSelected(row.getDocument());
        });

        // We reuse this view to show the "select a match" message to avoid having another view
        matchNumber = (TextView) view.findViewById(R.id.match_number);
        matchNumber.setText("Please select a match.");

        scoutingTeam = (TextView) view.findViewById(R.id.scouting_team);
        scoutingTeam.setText("");

        beginScouting = (Button) view.findViewById(R.id.begin_scouting);
        beginScouting.setOnClickListener(this);
        beginScouting.setEnabled(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateAssignedTeam();

        try {
            runQuery();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error querying the match list. Check logcat!", Toast.LENGTH_LONG).show();
        }

        try {
            if (DatabaseManager.getInstance(getActivity()).isMatchScouted(selectedMatchKey, selectedTeamToScout)) {
                if (list.getItemAtPosition(currPosition + 1) != null) {
                    QueryRow row = (QueryRow) list.getItemAtPosition(currPosition + 1);
                    onMatchSelected(row.getDocument());
                }
            }
        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
        }
    }

    private void runQuery() throws Exception {
        Query query = DatabaseManager.getInstance(getActivity()).getAllMatches();

        adapter = new MatchListAdapter(getActivity(), new ArrayList<QueryRow>());

        QueryEnumerator enumerator = query.run();

        List<QueryRow> queryRows = new ArrayList<>();
        for (Iterator<QueryRow> it = enumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            queryRows.add(row);
        }

        Parcelable state = list.onSaveInstanceState();
        adapter = new MatchListAdapter(getActivity(), queryRows);
        list.setAdapter(adapter);
        list.onRestoreInstanceState(state);
    }

    private void onMatchSelected(Document matchDocument) {
        selectedMatchKey = (String) matchDocument.getProperty("key");

        int matchNumber = (Integer) matchDocument.getProperty("match_number");
        this.matchNumber.setText("Match " + matchNumber);

        selectedTeamToScout = Utilities.getAssignedTeamKeyFromMatchDocument(getActivity(), matchDocument);


        // Populates data window
        try {
            DatabaseManager db = DatabaseManager.getInstance(getActivity());
            Document testDocument = db.getMatchResults(selectedMatchKey, selectedTeamToScout);
            MatchDataView.initializeViewsInViewGroupWithDocument((ViewGroup) getView(), testDocument);
        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading data for team. Check LogCat.", Toast.LENGTH_LONG).show();
        }

        String teamToScout = selectedTeamToScout.replace("frc", "");
        scoutingTeam.setText(teamToScout);

        beginScouting.setEnabled(true);
        try {
            if (DatabaseManager.getInstance(getActivity()).isMatchScouted(selectedMatchKey, selectedTeamToScout)) {
                beginScouting.setText(R.string.rescout_match);
            } else {
                beginScouting.setText(R.string.being_scouting);
            }
        } catch (Exception e) {
            e.printStackTrace();
            beginScouting.setText(R.string.being_scouting);
        }
        beginScouting.setEnabled(true);

        // Construct the file path for the team's photo
        // Construct the file path for the team's photo
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wildrank/" + teamToScout + ".jpg";

        boolean isMatchScouted;
        try {
            isMatchScouted = DatabaseManager.getInstance(getActivity()).isMatchScouted(selectedMatchKey, selectedTeamToScout);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error determining if match is scouted already.", Toast.LENGTH_SHORT).show();
            isMatchScouted = false;
        }

// Check if the image file exists
        File imageFile = new File(imagePath);
        ScrollView window = (ScrollView) getView().findViewById(R.id.summary_window);
        if (imageFile.exists()) {
            // Get a reference to the ImageView in your layout
            ImageView teamPhotoImageView = (ImageView) getView().findViewById(R.id.scouting_team_photo);

            Picasso.get()
                    .load(new File(imagePath))
                    .placeholder(R.drawable.loading) // Set a placeholder image resource
                    .error(R.drawable.no_image_available) // Set an error image resource
                    .into(teamPhotoImageView);

            if (isMatchScouted) {
                teamPhotoImageView.setVisibility(View.GONE);
                window.setVisibility(View.VISIBLE);
            } else {
                teamPhotoImageView.setVisibility(View.VISIBLE);
                window.setVisibility(View.GONE);
            }
        } else {
            // If the image file doesn't exist, set a default image
            ImageView teamPhotoImageView = (ImageView) getView().findViewById(R.id.scouting_team_photo);
            teamPhotoImageView.setImageResource(R.drawable.no_image_available); // Set the default image resource
            if (isMatchScouted) {
                teamPhotoImageView.setVisibility(View.GONE);
                window.setVisibility(View.VISIBLE);
            } else {
                teamPhotoImageView.setVisibility(View.VISIBLE);
                window.setVisibility(View.GONE);
            }
        }
beginScouting.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.begin_scouting) {
            // Launch the scouting activity
            String allianceColor;
            String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeams", "red_1");
            if (assignedTeamType.contains("red")) {
                allianceColor = ScoutMatchActivity.EXTRA_ALLIANCE_COLOR_RED;
            } else {
                allianceColor = ScoutMatchActivity.EXTRA_ALLIANCE_COLOR_BLUE;
            }
            final Intent intent = ScoutMatchActivity.createIntent(getActivity(), selectedMatchKey, selectedTeamToScout, allianceColor);
            boolean isMatchScouted;
            try {
                isMatchScouted = DatabaseManager.getInstance(getActivity()).isMatchScouted(selectedMatchKey, selectedTeamToScout);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error determining if match is scouted already.", Toast.LENGTH_SHORT).show();
                isMatchScouted = false;
            }
            if (isMatchScouted) {
                // Prompt to overwrite data
                new AlertDialog.Builder(getActivity())
                        .setTitle("Rescouting match")
                        .setMessage("Existing match data will be overwritten if you continue.")
                        .setPositiveButton("Continue", (dialog, which) -> startActivity(intent))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
            } else {
                // Begin scouting as normal!
                startActivity(intent);

            }
        }
    }

    public void updateAssignedTeam() {
        String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");
        ((TextView) getView().findViewById(R.id.assigned_team)).setText(assignedTeamType);
    }
}
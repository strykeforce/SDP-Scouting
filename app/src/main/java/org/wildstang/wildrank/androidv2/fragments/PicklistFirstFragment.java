package org.wildstang.wildrank.androidv2.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.adapters.PicklistAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PicklistFirstFragment extends PicklistMainFragment {
    private ListView teamsList;
    private ListView picksList;
    private PicklistAdapter teamsAdapter;
    private PicklistAdapter picksAdapter;
    private ArrayList<String> teamsArray;
    private ArrayList<String> picksArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picklist_first, container, false);

        teamsArray = new ArrayList<>();
        picksArray = new ArrayList<>();

        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).contains("teamsArray_size")) {
            int teamsArrayStrings = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("teamsArray_size", 0);
            for(int m = 0; m < teamsArrayStrings; m++) {
                teamsArray.add(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("teamsArray_" + m, null));
            }
            int picksArrayStrings = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("picksArray_size", 0);
            for(int n = 0; n < picksArrayStrings; n++) {
                picksArray.add(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("picksArray_" + n, null));
            }
        }

        teamsList = (ListView) view.findViewById(R.id.teams_list);
        teamsAdapter = new PicklistAdapter(getActivity(), new ArrayList<>());
        teamsList.setAdapter(teamsAdapter);
        teamsList.setTransitionName("teamsList");

        picksList = (ListView) view.findViewById(R.id.picks_list);
        picksAdapter = new PicklistAdapter(getActivity(), new ArrayList<>());
        picksList.setAdapter(picksAdapter);
        picksList.setTransitionName("picksList");

        teamsList.setOnItemClickListener((parent, view1, position, id) -> {
            teamsList.setItemChecked(position, true);
            QueryRow row = (QueryRow) parent.getItemAtPosition(position);
            onTeamSelected(row.getDocument(), teamsList, view1, position);
        });

        teamsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startDrag(view, teamsList.getItemAtPosition(position), teamsAdapter);
                return true;
            }
        });

        teamsList.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                onTeamDragged(teamsList, picksList, event);
                return true;
            }
        });

        picksList.setOnItemClickListener((parent, view1, position, id) -> {
            picksList.setItemChecked(position, true);
            QueryRow row = (QueryRow) parent.getItemAtPosition(position);
            onTeamSelected(row.getDocument(), picksList, view1, position);
        });

        picksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startDrag(view, picksList.getItemAtPosition(position), picksAdapter);
                return true;
            }
        });

        picksList.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                onTeamDragged(picksList, teamsList, event);
                return true;
            }
        });

        picksList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                adjustTint(picksList);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            runQuery();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error querying the team list. Check logcat!", Toast.LENGTH_LONG).show();
        }
    }

    private void runQuery() throws Exception {
        if (!PreferenceManager.getDefaultSharedPreferences(getActivity()).contains("teamsArray_size")) {
            Query query = DatabaseManager.getInstance(getActivity()).getAllTeams();

            QueryEnumerator enumerator = query.run();

            List<QueryRow> queryRows = new ArrayList<>();
            for (Iterator<QueryRow> it = enumerator; it.hasNext(); ) {
                QueryRow row = it.next();
                teamsArray.add(row.getKey().toString());
                queryRows.add(row);
            }

            Parcelable teamsState = teamsList.onSaveInstanceState();
            teamsAdapter = new PicklistAdapter(getActivity(), queryRows);
            teamsList.setAdapter(teamsAdapter);
            teamsList.onRestoreInstanceState(teamsState);

            List<QueryRow> fillerRow = new ArrayList<>();

            Parcelable picksState = picksList.onSaveInstanceState();
            picksAdapter = new PicklistAdapter(getActivity(), fillerRow);
            picksList.setAdapter(picksAdapter);
            picksList.onRestoreInstanceState(picksState);
        } else {
            Query query = DatabaseManager.getInstance(getActivity()).getAllTeams();

            QueryEnumerator enumerator = query.run();

            List<QueryRow> teamsQueryRows = new ArrayList<>();
            List<QueryRow> picksQueryRows = new ArrayList<>();
            for (Iterator<QueryRow> it = enumerator; it.hasNext(); ) {
                QueryRow row = it.next();
                boolean teamsQueryRow = false;
                for(int i = 0; i < teamsArray.size(); i++) {
                    if (teamsArray.get(i).equals(row.getKey().toString())) {
                        teamsQueryRow = true;
                    }
                }
                if (teamsQueryRow) {
                    teamsQueryRows.add(row);
                } else picksQueryRows.add(row);
            }

            Parcelable teamsState = teamsList.onSaveInstanceState();
            teamsAdapter = new PicklistAdapter(getActivity(), teamsQueryRows);
            teamsList.setAdapter(teamsAdapter);
            teamsList.onRestoreInstanceState(teamsState);

            Parcelable picksState = picksList.onSaveInstanceState();
            picksAdapter = new PicklistAdapter(getActivity(), picksQueryRows);
            picksList.setAdapter(picksAdapter);
            picksList.onRestoreInstanceState(picksState);
        }
    }

    @Override
    public void onTeamSelected(Document doc, ListView list, View view, Integer position) {
        super.onTeamSelected(doc, list, view, position);
    }

    public void startDrag(View view, Object item, PicklistAdapter adapter) {
        super.startDrag(view, item, adapter);
    }

    public boolean onTeamDragged(ListView tList, ListView oList, DragEvent event) {
        CharSequence eventLabel = "";
        if (event.getClipDescription() != null) {
            eventLabel = event.getClipDescription().getLabel();
            System.out.println(event.getClipDescription().getLabel());
        }
        int preTeamsLength = teamsList.getCount();
        boolean done = super.onTeamDragged(tList, oList, event);
        int postTeamsLength = teamsList.getCount();
        if (preTeamsLength != postTeamsLength) {
            int start = -1;
            for(int i = 0; i < eventLabel.length(); i++) {
                if (Objects.equals((String) eventLabel.subSequence(i, i + 4), "key=")) {
                    start = i + 4;
                    break;
                }
            }
            String teamNumber = (String) eventLabel.subSequence(start, eventLabel.length() - 1);

            if (preTeamsLength > postTeamsLength) {
                changedList("teamsList", teamNumber);
            } else if (preTeamsLength < postTeamsLength) {
                changedList("picksList", teamNumber);
            }
        }
        return done;
    }

    public void adjustTint(ListView list) {
        super.adjustTint(list);
    }

    public void changedList(String switchedFrom, String teamNumber) {
        if (switchedFrom == "teamsList") {
            teamsArray.remove(teamNumber);
            picksArray.add(teamNumber);
        } else {
            teamsArray.add(teamNumber);
            picksArray.remove(teamNumber);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();

        if (prefs.contains("teamsArray_size")) {
            int teamsArrayStrings = prefs.getInt("teamsArray_size", 0);
            for(int m = 0; m < teamsArrayStrings; m++) {
                editor.remove("teamsArray_" + m);
            }
            editor.remove("teamsArray_size");
            int picksArrayStrings = prefs.getInt("picksArray_size", 0);
            for(int n = 0; n < picksArrayStrings; n++) {
                editor.remove("picksArray_" + n);
            }
            editor.remove("picksArray_size");
        }

        editor.putInt("teamsArray_size", teamsArray.size());
        for(int i = 0; i < teamsArray.size(); i++) {
            editor.putString("teamsArray_" + i, teamsArray.get(i));
        }
        editor.putInt("picksArray_size", picksArray.size());
        for(int j = 0; j < picksArray.size(); j++) {
            editor.putString("picksArray_" + j, picksArray.get(j));
        }

        editor.commit();
    }
}
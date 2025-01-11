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

public class PicklistSecondFragment extends PicklistMainFragment {
    private ListView teamsList;
    private ListView picksList;
    private PicklistAdapter teamsAdapter;
    private PicklistAdapter picksAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picklist_lists, container, false);

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
        Query query = DatabaseManager.getInstance(getActivity()).getAllTeams();

        QueryEnumerator enumerator = query.run();

        List<QueryRow> queryRows = new ArrayList<>();
        for (Iterator<QueryRow> it = enumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            queryRows.add(row);
        }

        if (!PreferenceManager.getDefaultSharedPreferences(getActivity()).contains("secondTeamsArray_size")) {
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
            List<QueryRow> teamsQueryRows = new ArrayList<>();
            for (int i = 0; i < PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("secondTeamsArray_size", 0); i++) {
                QueryRow teamsRow = null;
                String teamsNumber = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("secondTeamsArray_" + i, "");
                for (int m = 0; m < queryRows.size(); m++) {
                    if (Objects.equals(queryRows.get(m).getKey().toString(), teamsNumber)) {
                        teamsRow = queryRows.get(m);
                        m = queryRows.size();
                    }
                }
                teamsQueryRows.add(teamsRow);
            }

            Parcelable teamsState = teamsList.onSaveInstanceState();
            teamsAdapter = new PicklistAdapter(getActivity(), teamsQueryRows);
            teamsList.setAdapter(teamsAdapter);
            teamsList.onRestoreInstanceState(teamsState);

            List<QueryRow> picksQueryRows = new ArrayList<>();
            for (int j = 0; j < PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("secondPicksArray_size", 0); j++) {
                QueryRow picksRow = null;
                String picksNumber = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("secondPicksArray_" + j, "");
                for (int n = 0; n < queryRows.size(); n++) {
                    if (Objects.equals(queryRows.get(n).getKey().toString(), picksNumber)) {
                        picksRow = queryRows.get(n);
                        n = queryRows.size();
                    }
                }
                picksQueryRows.add(picksRow);
            }

            Parcelable picksState = picksList.onSaveInstanceState();
            picksAdapter = new PicklistAdapter(getActivity(), picksQueryRows);
            picksList.setAdapter(picksAdapter);
            picksList.onRestoreInstanceState(picksState);

            adjustTint(picksList);
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
        return super.onTeamDragged(tList, oList, event);
    }

    public void adjustTint(ListView list) {
        super.adjustTint(list);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();

        if (prefs.contains("secondTeamsArray_size")) {
            for(int m = 0; m < prefs.getInt("secondTeamsArray_size", 0); m++) {
                editor.remove("secondTeamsArray_" + m);
            }
            editor.remove("secondTeamsArray_size");
            for(int n = 0; n < prefs.getInt("secondPicksArray_size", 0); n++) {
                editor.remove("secondPicksArray_" + n);
            }
            editor.remove("secondPicksArray_size");
        }

        ArrayList<String> teamsArray = new ArrayList<>();
        for (int i = 0; i < teamsList.getAdapter().getCount(); i++) {
            teamsArray.add(((QueryRow) teamsList.getAdapter().getItem(i)).getKey().toString());
        }
        ArrayList<String> picksArray = new ArrayList<>();
        for (int j = 0; j < picksList.getAdapter().getCount(); j++) {
            picksArray.add(((QueryRow) picksList.getAdapter().getItem(j)).getKey().toString());
        }

        editor.putInt("secondTeamsArray_size", teamsArray.size());
        for(int b = 0; b < teamsArray.size(); b++) {
            editor.putString("secondTeamsArray_" + b, teamsArray.get(b));
        }
        editor.putInt("secondPicksArray_size", picksArray.size());
        for(int d = 0; d < picksArray.size(); d++) {
            editor.putString("secondPicksArray_" + d, picksArray.get(d));
        }

        editor.commit();
    }
}
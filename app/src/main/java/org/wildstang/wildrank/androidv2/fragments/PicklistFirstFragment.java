package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.os.Parcelable;
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

public class PicklistFirstFragment extends PicklistMainFragment {
    private ListView teamsList;
    private ListView picksList;
    private PicklistAdapter teamsAdapter;
    private PicklistAdapter picksAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picklist_first, container, false);

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

        Parcelable teamsState = teamsList.onSaveInstanceState();
        teamsAdapter = new PicklistAdapter(getActivity(), queryRows);
        teamsList.setAdapter(teamsAdapter);
        teamsList.onRestoreInstanceState(teamsState);

        List<QueryRow> fillerRow = new ArrayList<>();

        Parcelable picksState = picksList.onSaveInstanceState();
        picksAdapter = new PicklistAdapter(getActivity(), fillerRow);
        picksList.setAdapter(picksAdapter);
        picksList.onRestoreInstanceState(picksState);
    }

    @Override
    public void onTeamSelected(Document doc, ListView list, View view, Integer position) {
        super.onTeamSelected(doc, list, view, position);
    }

    public void startDrag(View view, Object item, PicklistAdapter adapter) {
        super.startDrag(view, item, adapter);
    }

    public boolean onTeamDragged(ListView tList, ListView oList, DragEvent event) {
        return(super.onTeamDragged(tList, oList, event));
    }
}

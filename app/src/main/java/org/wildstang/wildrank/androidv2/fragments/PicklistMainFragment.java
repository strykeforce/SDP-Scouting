package org.wildstang.wildrank.androidv2.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.adapters.PicklistAdapter;
import org.wildstang.wildrank.androidv2.adapters.PicklistFragmentPagerAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.views.SlidingTabs;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.io.IOException;
import java.util.List;

public class PicklistMainFragment extends Fragment {
    private ViewPager pager;
    private SlidingTabs tabs;
    private PicklistAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picklist_main, container, false);
        pager = (ViewPager) view.findViewById(R.id.view_pager);
        tabs = (SlidingTabs) view.findViewById(R.id.tabs);

        pager.setOffscreenPageLimit(10);

        pager.setAdapter(new PicklistFragmentPagerAdapter(getFragmentManager()));
        tabs.setViewPager(pager);

        String team = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");

        if (team.contains("red")) {
            tabs.setBackgroundColor(getResources().getColor(R.color.material_red));
        } else {
            tabs.setBackgroundColor(getResources().getColor(R.color.material_blue));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onTeamSelected(Document doc) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        ViewGroup dialogView = (ViewGroup) inflater.inflate(R.layout.fragment_summaries_data, null);
        List<Document> matchDocs = null;
        try {
            DatabaseManager db = DatabaseManager.getInstance(getActivity());
            matchDocs = db.getMatchResultsForTeam((String) doc.getProperty("key"));
        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading data for team. Check LogCat.", Toast.LENGTH_LONG).show();
        }
        MatchDataView.initializeViewsInViewGroupWithDocuments(dialogView, matchDocs);
        AlertDialog.Builder info = new AlertDialog.Builder(getActivity()).setView(dialogView).setNegativeButton("Exit", (dialog, which) -> dialog.dismiss());
        info.show();
    }

    public  void startDrag(View view, Object item) {
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDragAndDrop(null, shadowBuilder, item, 0);
    }

    public boolean onTeamDragged(ListView list, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                PicklistAdapter sourceAdapter = (PicklistAdapter) ((ListView) event.getLocalState()).getAdapter();
                PicklistAdapter targetAdapter = (PicklistAdapter) list.getAdapter();
                int position = list.pointToPosition((int) event.getX(), (int) event.getY());
                if (position != ListView.INVALID_POSITION) {
                    QueryRow draggedItem = (QueryRow) event.getLocalState();
                    sourceAdapter.remove(draggedItem);
                    targetAdapter.insert(draggedItem, position);
                    targetAdapter.notifyDataSetChanged();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
}

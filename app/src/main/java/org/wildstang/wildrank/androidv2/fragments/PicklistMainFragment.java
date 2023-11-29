package org.wildstang.wildrank.androidv2.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Pair;
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
import java.util.ArrayList;
import java.util.List;

public class PicklistMainFragment extends Fragment {
    private ViewPager pager;
    private SlidingTabs tabs;
    private PicklistAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picklist_main, container, false);

        listAdapter = new PicklistAdapter(getActivity(), new ArrayList<>());

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

    public void onTeamSelected(Document doc, ListView list, View view) {
        if (list.getTransitionName().equals("teamsList")) {
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
        } else if (list.getTransitionName().equals("picksList")) {
            Drawable background = view.getBackground();

            int[] stateSet = {android.R.attr.state_enabled};
            background.setState(stateSet);

            if (((ColorDrawable) background.getCurrent()).getColor() == 0) {
                view.setBackgroundColor(getResources().getColor(R.color.black_tint));
            } else if (((ColorDrawable) background.getCurrent()).getColor() == getResources().getColor(R.color.black_tint)) {
                view.setBackgroundColor(0);
            }
        }
    }

    public  void startDrag(View view, Object item, PicklistAdapter adapter) {
        QueryRow queryRow = (QueryRow) item;

        ClipData.Item clipItem = new ClipData.Item(item.toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData dragData = new ClipData(item.toString(), mimeTypes, clipItem);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view) {
            @Override
            public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
                outShadowSize.set(view.getWidth(), view.getHeight());
                outShadowTouchPoint.set((int) (view.getWidth() * 0.25), view.getHeight() / 2);
            }
        };
        view.startDragAndDrop(dragData, shadowBuilder, new Pair<>(queryRow, adapter), 0);
    }

    public boolean onTeamDragged(ListView list, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                if (list.getAdapter() != null) {
                    Pair<QueryRow, PicklistAdapter> localState = (Pair<QueryRow, PicklistAdapter>) event.getLocalState();
                    QueryRow draggedItem = localState.first;
                    PicklistAdapter sourceAdapter = localState.second;
                    PicklistAdapter targetAdapter = (PicklistAdapter) list.getAdapter();

                    if (targetAdapter.getCount() == 0) {
                        sourceAdapter.remove(draggedItem);
                        targetAdapter.add(draggedItem);
                    } else {
                        int position = list.pointToPosition((int) event.getX(), (int) event.getY());
                        if (position != ListView.INVALID_POSITION) {
                            sourceAdapter.remove(draggedItem);
                            targetAdapter.insert(draggedItem, position);
                            targetAdapter.notifyDataSetChanged();
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}

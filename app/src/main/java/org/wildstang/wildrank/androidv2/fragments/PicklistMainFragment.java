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
import java.util.Objects;

public class PicklistMainFragment extends Fragment {
    private ViewPager pager;
    private SlidingTabs tabs;
    private PicklistAdapter listAdapter;
    private ArrayList<String> picked = new ArrayList<>();
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

    public void onTeamSelected(Document doc, ListView list, View view, Integer position) {
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
                picked.add(((PicklistAdapter.ViewHolder) view.getTag()).getNumber());
            } else if (((ColorDrawable) background.getCurrent()).getColor() == getResources().getColor(R.color.black_tint)) {
                view.setBackgroundColor(0);
                picked.remove(((PicklistAdapter.ViewHolder) view.getTag()).getNumber());
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

    public void adjustTint(ListView list) {
        for (int i = 0; i < list.getChildCount(); i++) {
            boolean tint = false;
            for (int j = 0; j < picked.size(); j++) {
                if (Objects.equals(((PicklistAdapter.ViewHolder) list.getChildAt(i).getTag()).getNumber(), picked.get(j))) {
                    tint = true;
                }
            }
            if (tint) {
                list.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.black_tint));
            } else {
                list.getChildAt(i).setBackgroundColor(0);
            }
        }
    }

    public boolean onTeamDragged(ListView tList, ListView oList, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                if (tList.getAdapter() != null) {
                    Pair<QueryRow, PicklistAdapter> localState = (Pair<QueryRow, PicklistAdapter>) event.getLocalState();
                    QueryRow draggedItem = localState.first;
                    PicklistAdapter sourceAdapter = localState.second;
                    PicklistAdapter targetAdapter = (PicklistAdapter) tList.getAdapter();

                    if (targetAdapter.getCount() == 0) {
                        sourceAdapter.remove(draggedItem);
                        targetAdapter.insert(draggedItem, 0);
                    } else {
                        int position = tList.pointToPosition((int) event.getX(), (int) event.getY());
                        if (position != ListView.INVALID_POSITION) {
                            sourceAdapter.remove(draggedItem);
                            targetAdapter.insert(draggedItem, position);
                            targetAdapter.notifyDataSetChanged();
                            if (tList.getTransitionName().equals("teamsList")) {
                                if (sourceAdapter != targetAdapter) {
                                    adjustTint(oList);
                                }
                            } else if (tList.getTransitionName().equals("picksList")) {
                                adjustTint(tList);
                            }
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
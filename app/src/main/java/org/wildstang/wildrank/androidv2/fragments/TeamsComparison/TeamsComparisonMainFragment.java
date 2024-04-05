package org.wildstang.wildrank.androidv2.fragments.TeamsComparison;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.adapters.TeamsComparisonFragmentPagerAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.views.SlidingTabs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeamsComparisonMainFragment extends Fragment {
    private ViewPager pager;
    private SlidingTabs tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comparison_main, container, false);
        pager = (ViewPager) view.findViewById(R.id.view_pager);
        tabs = (SlidingTabs) view.findViewById(R.id.tabs);

        pager.setOffscreenPageLimit(10);
        pager.setAdapter(new TeamsComparisonFragmentPagerAdapter(getFragmentManager()));
        tabs.setViewPager(pager);

        String team = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("assignedTeam", "red_1");

        if (team.contains("red")) {
            tabs.setBackgroundColor(getResources().getColor(R.color.material_red));
        } else {
            tabs.setBackgroundColor(getResources().getColor(R.color.material_blue));
        }

        final Handler handler = new Handler();
        handler.postDelayed(() -> loadInfo(), 50);

        return view;
    }

    private void loadInfo() {
        try {
            DatabaseManager db = DatabaseManager.getInstance(getActivity());
            Query query = db.getAllTeams();
            QueryEnumerator enumerator = query.run();

            List<List<Document>> allMatchDocuments = new ArrayList<>();
            for (Iterator<QueryRow> it = enumerator; it.hasNext();) {
                allMatchDocuments.add((List<Document>) db.getMatchResultsForTeam("frc" + it.next().getKey()));
            }

            ((TeamsComparisonFragmentPagerAdapter) pager.getAdapter()).acceptNewData(allMatchDocuments);

        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading data. Check LogCat.", Toast.LENGTH_LONG).show();
        }
    }
}

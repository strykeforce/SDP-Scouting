package org.wildstang.wildrank.androidv2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesDataFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesGraphsFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesInfoFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesRawDataFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesAutosFragment;

import java.util.List;

public class TeamSummariesFragmentPagerAdapter extends FragmentStatePagerAdapter {

    static final int NUM_FRAGMENTS = 5;

    private TeamSummariesInfoFragment infoFragment;
    private TeamSummariesDataFragment dataFragment;
    private TeamSummariesGraphsFragment graphsFragment;
    private TeamSummariesAutosFragment autosFragment;
    private TeamSummariesRawDataFragment rawDataFragment;

    public TeamSummariesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        infoFragment = new TeamSummariesInfoFragment();
        dataFragment = new TeamSummariesDataFragment();
        graphsFragment = new TeamSummariesGraphsFragment();
        autosFragment = new TeamSummariesAutosFragment();
        rawDataFragment = new TeamSummariesRawDataFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return infoFragment;
            case 1:
                return dataFragment;
            case 2:
                return graphsFragment;
            case 3:
                return autosFragment;
            case 4:
                return rawDataFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "General";
            case 1:
                return "Data";
            case 2:
                return "Graphs";
            case 3:
                return "Unique Autos";
            case 4:
                return "Raw Data";
            default:
                return "ERROR INVALID POSITION";
        }
    }

    public void acceptNewTeamData(String teamKey, Document teamDoc, Document pitDoc, List<Document> matchDocs) {
        infoFragment.acceptNewTeamData(teamKey, teamDoc, pitDoc, matchDocs);
        dataFragment.acceptNewTeamData(teamKey, teamDoc, pitDoc, matchDocs);
        graphsFragment.acceptNewTeamData(teamKey, teamDoc, pitDoc, matchDocs);
        autosFragment.acceptNewTeamData(teamKey, teamDoc, pitDoc, matchDocs);
        rawDataFragment.acceptNewTeamData(teamKey, teamDoc, pitDoc, matchDocs);
    }

}
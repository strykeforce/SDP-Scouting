package org.wildstang.wildrank.androidv2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageAmpAndSpeakerFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageAmpFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageAutoFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageCyclesFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAveragePassesFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageSpeakerFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonAverageWeightedCyclesFragment;

import java.util.List;

public class TeamsComparisonFragmentPagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_FRAGMENTS = 6;

    private TeamsComparisonAverageAutoFragment averageAutoFragment;
    private TeamsComparisonAverageAmpFragment averageAmpFragment;
    private TeamsComparisonAverageSpeakerFragment averageSpeakerFragment;
    private TeamsComparisonAverageAmpAndSpeakerFragment averageAmpAndSpeakerFragment;
    private TeamsComparisonAveragePassesFragment averagePassesFragment;
    private TeamsComparisonAverageCyclesFragment averageCyclesFragment;
    private TeamsComparisonAverageWeightedCyclesFragment averageWeightedCyclesFragment;

    public TeamsComparisonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        averageAutoFragment = new TeamsComparisonAverageAutoFragment();
        averageAmpFragment = new TeamsComparisonAverageAmpFragment();
        averageSpeakerFragment = new TeamsComparisonAverageSpeakerFragment();
        averageAmpAndSpeakerFragment = new TeamsComparisonAverageAmpAndSpeakerFragment();
        averagePassesFragment = new TeamsComparisonAveragePassesFragment();
        averageCyclesFragment = new TeamsComparisonAverageCyclesFragment();
        averageWeightedCyclesFragment = new TeamsComparisonAverageWeightedCyclesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return averageAutoFragment;
            case 1:
                return averageAmpFragment;
            case 2:
                return averageSpeakerFragment;
            case 3:
                return averageAmpAndSpeakerFragment;
            case 4:
                return averagePassesFragment;
            case 5:
                return averageCyclesFragment;
            case 6:
                return averageWeightedCyclesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Average Auto";
            case 1:
                return "Average Amp";
            case 2:
                return "Average Speaker";
            case 3:
                return "Average Amp and Speaker";
            case 4:
                return "Average Passes";
            case 5:
                return "Average Cycles";
            case 6:
                return "Average Weighted Cycles";
            default:
                return "ERROR INVALID POSITION";
        }
    }

    public void acceptNewData(List<List<Document>> allMatchDocuments) {
        averageAutoFragment.acceptNewData(allMatchDocuments);
        averageAmpFragment.acceptNewData(allMatchDocuments);
        averageSpeakerFragment.acceptNewData(allMatchDocuments);
        averageAmpAndSpeakerFragment.acceptNewData(allMatchDocuments);
        averagePassesFragment.acceptNewData(allMatchDocuments);
        averageCyclesFragment.acceptNewData(allMatchDocuments);
        averageWeightedCyclesFragment.acceptNewData(allMatchDocuments);
    }
}

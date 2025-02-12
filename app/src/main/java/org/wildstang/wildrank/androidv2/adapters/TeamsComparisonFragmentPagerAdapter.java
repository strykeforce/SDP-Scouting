package org.wildstang.wildrank.androidv2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageNetFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageProcessorFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageRemovedAlgaeFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxNetFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxProcessorFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxRemovedAlgaeFragment;

import java.util.List;

public class TeamsComparisonFragmentPagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_FRAGMENTS = 8;

    private TeamsComparisonAverageRemovedAlgaeFragment averageRemovedAlgaeFragment;
    private TeamsComparisonAverageProcessorFragment averageProcessorFragment;
    private TeamsComparisonAverageNetFragment averageNetFragment;
    private TeamsComparisonAverageCoralFragment averageCoralFragment;
    private TeamsComparisonMaxRemovedAlgaeFragment maxRemovedAlgaeFragment;
    private TeamsComparisonMaxProcessorFragment maxProcessorFragment;
    private TeamsComparisonMaxNetFragment maxNetFragment;
    private TeamsComparisonMaxCoralFragment maxCoralFragment;

    public TeamsComparisonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        averageRemovedAlgaeFragment = new TeamsComparisonAverageRemovedAlgaeFragment();
        averageProcessorFragment = new TeamsComparisonAverageProcessorFragment();
        averageNetFragment = new TeamsComparisonAverageNetFragment();
        averageCoralFragment = new TeamsComparisonAverageCoralFragment();
        maxRemovedAlgaeFragment = new TeamsComparisonMaxRemovedAlgaeFragment();
        maxProcessorFragment = new TeamsComparisonMaxProcessorFragment();
        maxNetFragment = new TeamsComparisonMaxNetFragment();
        maxCoralFragment = new TeamsComparisonMaxCoralFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return averageRemovedAlgaeFragment;
            case 1:
                return averageProcessorFragment;
            case 2:
                return averageNetFragment;
            case 3:
                return averageCoralFragment;
            case 4:
                return maxRemovedAlgaeFragment;
            case 5:
                return maxProcessorFragment;
            case 6:
                return maxNetFragment;
            case 7:
                return maxCoralFragment;
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
                return "Average Removed Algae";
            case 1:
                return "Average Processor";
            case 2:
                return "Average Net";
            case 3:
                return "Average Coral";
            case 4:
                return "Max Removed Algae";
            case 5:
                return "Max Processor";
            case 6:
                return "Max Net";
            case 7:
                return "Max Coral";
            default:
                return "ERROR INVALID POSITION";
        }
    }

    public void acceptNewData(List<List<Document>> allMatchDocuments) {
        averageRemovedAlgaeFragment.acceptNewData(allMatchDocuments);
        averageProcessorFragment.acceptNewData(allMatchDocuments);
        averageNetFragment.acceptNewData(allMatchDocuments);
        averageCoralFragment.acceptNewData(allMatchDocuments);
        maxRemovedAlgaeFragment.acceptNewData(allMatchDocuments);
        maxProcessorFragment.acceptNewData(allMatchDocuments);
        maxNetFragment.acceptNewData(allMatchDocuments);
        maxCoralFragment.acceptNewData(allMatchDocuments);
    }
}

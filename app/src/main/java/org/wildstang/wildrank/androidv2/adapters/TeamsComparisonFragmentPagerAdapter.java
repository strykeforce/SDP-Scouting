package org.wildstang.wildrank.androidv2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageAutoCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageBargeFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageCombinedCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageNetFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageProcessorFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageRemovedAlgaeFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonAverageTeleCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxAutoCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxBargeFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxCombinedCoralFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxNetFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxProcessorFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxRemovedAlgaeFragment;
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape.TeamsComparisonMaxTeleCoralFragment;

import java.util.List;

public class TeamsComparisonFragmentPagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_FRAGMENTS = 14;

    private TeamsComparisonAverageRemovedAlgaeFragment averageRemovedAlgaeFragment;
    private TeamsComparisonAverageProcessorFragment averageProcessorFragment;
    private TeamsComparisonAverageNetFragment averageNetFragment;
    private TeamsComparisonAverageAutoCoralFragment averageAutoCoralFragment;
    private TeamsComparisonAverageTeleCoralFragment averageTeleCoralFragment;
    private TeamsComparisonAverageCombinedCoralFragment averageCombinedCoralFragment;
    private TeamsComparisonAverageBargeFragment averageBargeFragment;
    private TeamsComparisonMaxRemovedAlgaeFragment maxRemovedAlgaeFragment;
    private TeamsComparisonMaxProcessorFragment maxProcessorFragment;
    private TeamsComparisonMaxNetFragment maxNetFragment;
    private TeamsComparisonMaxAutoCoralFragment maxAutoCoralFragment;
    private TeamsComparisonMaxTeleCoralFragment maxTeleCoralFragment;
    private TeamsComparisonMaxCombinedCoralFragment maxCombinedCoralFragment;
    private TeamsComparisonMaxBargeFragment maxBargeFragment;

    public TeamsComparisonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        averageRemovedAlgaeFragment = new TeamsComparisonAverageRemovedAlgaeFragment();
        averageProcessorFragment = new TeamsComparisonAverageProcessorFragment();
        averageNetFragment = new TeamsComparisonAverageNetFragment();
        averageAutoCoralFragment = new TeamsComparisonAverageAutoCoralFragment();
        averageTeleCoralFragment = new TeamsComparisonAverageTeleCoralFragment();
        averageCombinedCoralFragment = new TeamsComparisonAverageCombinedCoralFragment();
        averageBargeFragment = new TeamsComparisonAverageBargeFragment();
        maxRemovedAlgaeFragment = new TeamsComparisonMaxRemovedAlgaeFragment();
        maxProcessorFragment = new TeamsComparisonMaxProcessorFragment();
        maxNetFragment = new TeamsComparisonMaxNetFragment();
        maxAutoCoralFragment = new TeamsComparisonMaxAutoCoralFragment();
        maxTeleCoralFragment = new TeamsComparisonMaxTeleCoralFragment();
        maxCombinedCoralFragment = new TeamsComparisonMaxCombinedCoralFragment();
        maxBargeFragment = new TeamsComparisonMaxBargeFragment();
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
                return averageAutoCoralFragment;
            case 4:
                return averageTeleCoralFragment;
            case 5:
                return averageCombinedCoralFragment;
            case 6:
                return averageBargeFragment;
            case 7:
                return maxRemovedAlgaeFragment;
            case 8:
                return maxProcessorFragment;
            case 9:
                return maxNetFragment;
            case 10:
                return maxAutoCoralFragment;
            case 11:
                return maxTeleCoralFragment;
            case 12:
                return maxCombinedCoralFragment;
            case 13:
                return maxBargeFragment;
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
                return "Average Auto Coral";
            case 4:
                return "Average Tele Coral";
            case 5:
                return "Average Combined Coral";
            case 6:
                return "Average Barge";
            case 7:
                return "Max Removed Algae";
            case 8:
                return "Max Processor";
            case 9:
                return "Max Net";
            case 10:
                return "Max Auto Coral";
            case 11:
                return "Max Tele Coral";
            case 12:
                return "Max Combined Coral";
            case 13:
                return "Max Barge";
            default:
                return "ERROR INVALID POSITION";
        }
    }

    public void acceptNewData(List<List<Document>> allMatchDocuments) {
        averageRemovedAlgaeFragment.acceptNewData(allMatchDocuments);
        averageProcessorFragment.acceptNewData(allMatchDocuments);
        averageNetFragment.acceptNewData(allMatchDocuments);
        averageAutoCoralFragment.acceptNewData(allMatchDocuments);
        averageTeleCoralFragment.acceptNewData(allMatchDocuments);
        averageCombinedCoralFragment.acceptNewData(allMatchDocuments);
        averageBargeFragment.acceptNewData(allMatchDocuments);
        maxRemovedAlgaeFragment.acceptNewData(allMatchDocuments);
        maxProcessorFragment.acceptNewData(allMatchDocuments);
        maxNetFragment.acceptNewData(allMatchDocuments);
        maxAutoCoralFragment.acceptNewData(allMatchDocuments);
        maxTeleCoralFragment.acceptNewData(allMatchDocuments);
        maxCombinedCoralFragment.acceptNewData(allMatchDocuments);
        maxBargeFragment.acceptNewData(allMatchDocuments);
    }
}

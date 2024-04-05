package org.wildstang.wildrank.androidv2.fragments.TeamSummaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.views.data.crescendo.AutosDataView;

import java.util.List;

public class TeamSummariesAutosFragment extends TeamSummariesFragment {
    AutosDataView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto_path, container, false);
        view = (AutosDataView) v.findViewById(R.id.autosview);
        return v;
    }

    @Override
    public void acceptNewTeamData(String teamKey, Document teamDoc, Document pitDoc, List<Document> matchDocs) {
        view.clearValue();
        view.calculateFromDocuments(matchDocs);
    }
}

package org.wildstang.wildrank.androidv2.fragments.TeamsComparison;

import android.support.v4.app.Fragment;

import com.couchbase.lite.Document;

import java.util.List;

public abstract class TeamsComparisonFragment extends Fragment {
    List<List<Document>> documents;

    public void update(List<List<Document>> documents) {
        this.documents = documents;
    }

    public abstract void acceptNewData(List<List<Document>> allMatchDocuments);
}

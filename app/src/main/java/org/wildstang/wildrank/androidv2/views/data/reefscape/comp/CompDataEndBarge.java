package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataEndBarge extends MatchDataView implements IMatchDataView {
    public CompDataEndBarge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int none = 0;
        int parks = 0;
        int shallows = 0;
        int deeps = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("barge") == null) {
                continue;
            }
            if (((String) data.get("barge")).equals("1. Not Parked")) {
                none++;
            } else if (((String) data.get("barge")).equals("2. Parked")) {
                parks++;
            } else if (((String) data.get("barge")).equals("3. Shallow Cage")) {
                shallows++;
            } else if (((String) data.get("barge")).equals("4. Deep Cage")) {
                deeps++;
            }
        }
        double nonePercentage = ((double) none / (double) documents.size());
        double parkedPercentage = ((double) parks / (double) documents.size());
        double shallowPercentage = ((double) shallows / (double) documents.size());
        double deepPercentage = ((double) deeps / (double) documents.size());
        setValueText("Not Parked   " + formatNumberAsString(none) + "/" + formatNumberAsString(documents.size()) + " --> " + formatPercentageAsString(nonePercentage) + "\n"
                + "Parked   " + formatNumberAsString(parks) + "/" + formatNumberAsString(documents.size()) + " --> " + formatPercentageAsString(parkedPercentage) + "\n"
                + "Shallow Cage   " + formatNumberAsString(shallows) + "/" + formatNumberAsString(documents.size()) + " --> " + formatPercentageAsString(shallowPercentage) + "\n"
                + "Deep Cage   " + formatNumberAsString(deeps) + "/" + formatNumberAsString(documents.size()) + " --> " + formatPercentageAsString(deepPercentage) + "\n"
                , "gray");
    }

    public void calculateFromDocument(Document document) {}
}

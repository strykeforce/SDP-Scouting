package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataEndStage extends MatchDataView implements IMatchDataView {
    public CompDataEndStage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int none = 0;
        int parks = 0;
        int onstages = 0;
        int harmonies = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("Stage") == null) {
                return;
            }
            if (((String) data.get("Stage")).equals("1. Not Parked")) {
                none++;
            } else if (((String) data.get("Stage")).equals("2. Parked")) {
                parks++;
            } else if (((String) data.get("Stage")).equals("3. Onstage")) {
                onstages++;
            } else if (((String) data.get("Stage")).equals("4. Onstage Harmony")) {
                harmonies++;
            }
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double nonePercentage = ((double) none / (double) matches);
            double parkedPercentage = ((double) parks / (double) matches);
            double onstagePercentage = ((double) onstages / (double) matches);
            double harmonyPercentage = ((double) harmonies / (double) matches);
            setValueText(formatNumberAsString(none) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(nonePercentage) + "\n"
                    + formatNumberAsString(parks) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(parkedPercentage) + "\n"
                    + formatNumberAsString(onstages) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(onstagePercentage) + "\n"
                    + formatNumberAsString(harmonies) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(harmonyPercentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

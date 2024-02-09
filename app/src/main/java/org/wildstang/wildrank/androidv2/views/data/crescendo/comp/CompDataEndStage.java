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
        int Buddy = 0;
        int Buddies = 0;
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
            } else if (((String) data.get("Stage")).equals("4. Onstage w/ Buddy")) {
                Buddy++;
            } else if (((String) data.get("Stage")).equals("5. Onstage w/ 2 Buddies")) {
            Buddies++;
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
            double BuddyPercentage = ((double) Buddy / (double) matches);
            double BuddiesPercentage = ((double) Buddies / (double) matches);
            setValueText("Not Parked   " + formatNumberAsString(none) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(nonePercentage) + "\n" +
                    "Parked   " + formatNumberAsString(parks) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(parkedPercentage) + "\n"
                    + "Onstage   " + formatNumberAsString(onstages) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(onstagePercentage) + "\n"
                    + "With Buddy   " + formatNumberAsString(Buddy) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(BuddyPercentage)
                    + "\n" + "With Buddies   " + formatNumberAsString(Buddies) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(BuddiesPercentage)
                    , "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

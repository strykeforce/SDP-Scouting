package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataEndChargePlatform extends MatchDataView implements IMatchDataView {
    public CompDataEndChargePlatform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int none = 0;
        int parks = 0;
        int tips = 0;
        int engages = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("EndChargePlatform") == null) {
                return;
            }
            if (((String) data.get("EndChargePlatform")).equals("1. Not Parked")) {
                none++;
            } else if (((String) data.get("EndChargePlatform")).equals("2. Parked")) {
                parks++;
            } else if (((String) data.get("EndChargePlatform")).equals("3. Charge Pad Tipped")) {
                tips++;
            } else if (((String) data.get("EndChargePlatform")).equals("4. Charge Pad Engaged")) {
                engages++;
            }
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double nonePercentage = ((double) none / (double) matches);
            double parkedPercentage = ((double) parks / (double) matches);
            double tippedPercentage = ((double) tips / (double) matches);
            double engagedPercentage = ((double) engages / (double) matches);
            setValueText(formatNumberAsString(none) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(nonePercentage) + "\n"
                    + formatNumberAsString(parks) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(parkedPercentage) + "\n"
                    + formatNumberAsString(tips) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(tippedPercentage) + "\n"
                    + formatNumberAsString(engages) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(engagedPercentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

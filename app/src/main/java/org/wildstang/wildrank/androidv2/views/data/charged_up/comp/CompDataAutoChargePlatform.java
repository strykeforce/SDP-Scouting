package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoChargePlatform extends MatchDataView implements IMatchDataView {
    public CompDataAutoChargePlatform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int docks = 0;
        int balances = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("Docked") == null || data.get("Balanced") == null) {
                return;
            }
            int docked = (boolean) data.get("Docked") ? 1 : 0;
            docks += docked;
            int balanced = (boolean) data.get("Balanced") ? 1 : 0;
            balances += balanced;
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            int none = (matches - docks - balances);
            double nonePercentage = ((double) none / (double) matches);
            double dockedPercentage = ((double) docks / (double) matches);
            double balancedPercentage = ((double) balances / (double) matches);
            setValueText(formatNumberAsString(none) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(nonePercentage) + "\n"
                    + formatNumberAsString(docks) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(dockedPercentage) + "\n"
                    + formatNumberAsString(balances) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(balancedPercentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

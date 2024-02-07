package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataEndHighNotes extends MatchDataView implements IMatchDataView {
    public CompDataEndHighNotes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int zero = 0;
        int one = 0;
        int two = 0;
        int three = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("HighNotes") == null) {
                return;
            }
            if (((String) data.get("HighNotes")).equals("\t\t0\t\t")) {
                zero++;
            } else if (((String) data.get("HighNotes")).equals("\t\t1\t\t")) {
                one++;
            } else if (((String) data.get("HighNotes")).equals("\t\t2\t\t")) {
                two++;
            } else if (((String) data.get("HighNotes")).equals("\t\t3\t\t")) {
                three++;
            }
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double zeroPercentage = ((double) zero / (double) matches);
            double onePercentage = ((double) one / (double) matches);
            double twoPercentage = ((double) two / (double) matches);
            double threePercentage = ((double) three / (double) matches);
            setValueText(formatNumberAsString(zero) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(zeroPercentage) + "\n"
                    + formatNumberAsString(one) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(onePercentage) + "\n"
                    + formatNumberAsString(two) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(twoPercentage) + "\n"
                    + formatNumberAsString(three) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(threePercentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

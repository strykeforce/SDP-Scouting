package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoCrossedLine extends MatchDataView implements IMatchDataView {
    public CompDataAutoCrossedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int crosses = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("CrossLine") == null) {
                return;
            }
            int cross = (boolean) data.get("CrossLine") ? 1 : 0;
            crosses += cross;
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double percentage = ((double) crosses / (double) matches);
            setValueText(formatNumberAsString(crosses) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(percentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

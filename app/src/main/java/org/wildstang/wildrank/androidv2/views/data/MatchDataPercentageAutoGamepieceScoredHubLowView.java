package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;


public class MatchDataPercentageAutoGamepieceScoredHubLowView extends MatchDataView implements IMatchDataView {

    public MatchDataPercentageAutoGamepieceScoredHubLowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int lowerScores = 0;
        int lowerMisses = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null || data.get("auto-score_cargo_low") == null || data.get("auto-miss_cargo_low") == null) {
                return;
            }
            int scored = (int) data.get("auto-score_cargo_low");
            int missed = (int) data.get("auto-miss_cargo_low");
            lowerScores += scored;
            lowerMisses += missed;
            didSomething = true;
        }
        if (lowerScores + lowerMisses == 0) {
            setValueText("N/A", "gray");
        } else {
            double percentage = ((double) lowerScores / (lowerScores + lowerMisses));
            setValueText(formatNumberAsString(lowerScores/documents.size()) +" --> (" +  formatPercentageAsString(percentage) + ")", "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;


public class MatchDataPercentageAutoGamepieceScoredHubHighView extends MatchDataView implements IMatchDataView {

    public MatchDataPercentageAutoGamepieceScoredHubHighView(Context context, AttributeSet attrs) {
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
        int upperScores = 0;
        int upperMisses = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null || data.get("auto-score_cargo_high") == null || data.get("auto-miss_cargo_high") == null) {
                return;
            }
            int scored = (int) data.get("auto-score_cargo_high");
            int missed = (int) data.get("auto-miss_cargo_high");
            upperScores += scored;
            upperMisses += missed;
            didSomething = true;
        }
        if (upperScores + upperMisses == 0) {
            setValueText("N/A", "gray");
        } else {
            double percentage = ((double) upperScores / (upperScores + upperMisses));
            setValueText(formatNumberAsString(upperScores/documents.size()) +" --> (" +  formatPercentageAsString(percentage) + ")", "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

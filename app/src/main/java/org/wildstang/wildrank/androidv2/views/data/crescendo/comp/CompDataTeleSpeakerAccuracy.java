package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleSpeakerAccuracy extends MatchDataView implements IMatchDataView {
    public CompDataTeleSpeakerAccuracy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int makes = 0;
        int misses = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_made_speaker") == null || data.get("tele_missed_speaker") == null) {
                return;
            }
            makes += (int) data.get("tele_made_speaker");
            misses += (int) data.get("tele_missed_speaker");
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            int shots = makes + misses;
            double accuracy = ((double) makes / (double) shots);
            setValueText("Total Shots: " + formatNumberAsString(shots) + "\n"
                    + formatNumberAsString(makes) + "/" + formatNumberAsString(shots) + " --> " + formatPercentageAsString(accuracy), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

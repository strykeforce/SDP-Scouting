package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoStartingZone extends MatchDataView implements IMatchDataView {
    public CompDataAutoStartingZone(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int exits = 0;
        int matches = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_move") == null) {
                return;
            }
            int exit = (boolean) data.get("auto_move") ? 1 : 0;
            exits += exit;
            matches++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double percentage = ((double) exits / (double) matches);
            setValueText(formatNumberAsString(exits) + "/" + formatNumberAsString(matches) + " --> " + formatPercentageAsString(percentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleConesAverage extends MatchDataView implements IMatchDataView {
    public CompDataTeleConesAverage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int cones = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_top_cones") == null || data.get("tele_middle_cones") == null || data.get("tele_bottom_cones") == null) {
                return;
            }
            cones += (int) data.get("tele_top_cones");
            cones += (int) data.get("tele_middle_cones");
            cones += (int) data.get("tele_bottom_cones");
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double average = (double) cones / (double) documents.size();
            setValueText(formatNumberAsString(average), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

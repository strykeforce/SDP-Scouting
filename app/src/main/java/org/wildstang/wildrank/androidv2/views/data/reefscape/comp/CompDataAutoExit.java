package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoExit extends MatchDataView implements IMatchDataView {
    public CompDataAutoExit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int exits = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_exit") == null) {
                continue;
            }
            int exit = (boolean) data.get("auto_exit") ? 1 : 0;
            exits += exit;
        }
        double percentage = ((double) exits / (double) documents.size());
        setValueText(formatNumberAsString(exits) + "/" + formatNumberAsString(documents.size()) + " --> " + formatPercentageAsString(percentage), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

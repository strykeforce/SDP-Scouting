package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoConesMax extends MatchDataView implements IMatchDataView {
    public CompDataAutoConesMax(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int max = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_top_cones") == null || data.get("auto_middle_cones") == null || data.get("auto_bottom_cones") == null) {
                return;
            }
            int cones = 0;
            cones += (int) data.get("auto_top_cones");
            cones += (int) data.get("auto_middle_cones");
            cones += (int) data.get("auto_bottom_cones");
            if (cones > max) {
                max = cones;
            }
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(formatNumberAsString(max), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

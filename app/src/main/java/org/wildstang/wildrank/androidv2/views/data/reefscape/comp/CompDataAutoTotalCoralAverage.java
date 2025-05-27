package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoTotalCoralAverage extends MatchDataView implements IMatchDataView {
    public CompDataAutoTotalCoralAverage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int coral = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_level_one") == null || data.get("auto_level_two") == null || data.get("auto_level_three") == null || data.get("auto_level_four") == null) {
                continue;
            }
            coral += (int) data.get("auto_level_one");
            coral += (int) data.get("auto_level_two");
            coral += (int) data.get("auto_level_three");
            coral += (int) data.get("auto_level_four");
        }
        double average = (double) coral / (double) documents.size();
        setValueText(formatNumberAsString(average), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

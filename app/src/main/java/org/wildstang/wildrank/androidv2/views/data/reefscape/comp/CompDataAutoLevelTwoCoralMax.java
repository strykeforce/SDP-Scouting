package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoLevelTwoCoralMax extends MatchDataView implements IMatchDataView {
    public CompDataAutoLevelTwoCoralMax(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int max = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_level_two") == null) {
                continue;
            }
            int levelTwo = (int) data.get("auto_level_two");
            if (levelTwo > max) {
                max = levelTwo;
            }
        }
        setValueText(formatNumberAsString(max), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

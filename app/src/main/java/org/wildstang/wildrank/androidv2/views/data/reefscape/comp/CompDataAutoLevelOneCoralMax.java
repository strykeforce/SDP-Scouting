package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoLevelOneCoralMax extends MatchDataView implements IMatchDataView {
    public CompDataAutoLevelOneCoralMax(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int max = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_level_one") == null) {
                continue;
            }
            int levelOne = (int) data.get("auto_level_one");
            if (levelOne > max) {
                max = levelOne;
            }
        }
        setValueText(formatNumberAsString(max), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

package org.wildstang.wildrank.androidv2.views.data.reefscape.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataTeleLevelFourCoral extends MatchDataView implements IMatchDataView {

    public MatchDataTeleLevelFourCoral(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    @Override
    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("tele_level_four") == null) {
            setValueText("N/A","gray");
            return;
        }
        int levelFour = (int) data.get("tele_level_four");
        setValueText(formatNumberAsString(levelFour), "gray");
    }
}
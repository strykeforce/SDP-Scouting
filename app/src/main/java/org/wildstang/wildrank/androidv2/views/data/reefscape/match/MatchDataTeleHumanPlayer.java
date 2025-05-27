package org.wildstang.wildrank.androidv2.views.data.reefscape.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataTeleHumanPlayer extends MatchDataView implements IMatchDataView {

    public MatchDataTeleHumanPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("human_number") == null) {
            setValueText("N/A","gray");
            return;
        }
        String number = (String) data.get("human_number");
        if (number == "-") {
            setValueText("Not Selected", "gray");
        } else setValueText(number, "gray");
    }
}
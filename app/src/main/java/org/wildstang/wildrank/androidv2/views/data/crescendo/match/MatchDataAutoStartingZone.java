package org.wildstang.wildrank.androidv2.views.data.crescendo.match;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataAutoStartingZone extends MatchDataView implements IMatchDataView {

    public MatchDataAutoStartingZone(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("auto_move") == null) {
            return;
        }
        boolean exited = (boolean) data.get("auto_move");
        didSomething = true;
        setValueText("" + exited, "gray");
    }
}
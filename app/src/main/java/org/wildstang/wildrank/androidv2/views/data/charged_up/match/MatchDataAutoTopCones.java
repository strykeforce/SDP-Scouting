package org.wildstang.wildrank.androidv2.views.data.charged_up.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.fragments.MatchScoutingMainFragment;
import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MatchDataAutoTopCones extends MatchDataView implements IMatchDataView {

    public MatchDataAutoTopCones(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int autoTopCones = 0;
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("auto_top_cones") == null) {
            return;
        }
        autoTopCones = (int) data.get("auto_top_cones");
        didSomething = true;
        setValueText(formatNumberAsString(autoTopCones), "gray");
    }
}
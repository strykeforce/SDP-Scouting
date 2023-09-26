package org.wildstang.wildrank.androidv2.views.data.charged_up;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataAutoBalanced extends MatchDataView implements IMatchDataView {

    public MatchDataAutoBalanced(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("Balanced") == null) {
            return;
        }
        Object checked = data.get("Balanced");
        didSomething = true;
        setValueText("" + checked, "gray");
    }
}
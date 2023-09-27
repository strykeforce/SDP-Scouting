package org.wildstang.wildrank.androidv2.views.data.charged_up.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataAutoMiddleCubes extends MatchDataView implements IMatchDataView {

    public MatchDataAutoMiddleCubes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int autoMiddleCubes = 0;
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("auto_middle_cubes") == null) {
            return;
        }
        autoMiddleCubes = (int) data.get("auto_middle_cubes");
        didSomething = true;
        setValueText("" + formatNumberAsString(autoMiddleCubes), "gray");
    }
}
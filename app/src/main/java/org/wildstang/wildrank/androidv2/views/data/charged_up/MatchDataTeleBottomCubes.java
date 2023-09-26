package org.wildstang.wildrank.androidv2.views.data.charged_up;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataTeleBottomCubes extends MatchDataView implements IMatchDataView {

    public MatchDataTeleBottomCubes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {
        if (document == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int teleBottomCubes = 0;
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("tele_bottom_cubes") == null) {
            return;
        }
        teleBottomCubes = (int) data.get("tele_bottom_cubes");
        didSomething = true;
        setValueText("" + formatNumberAsString(teleBottomCubes), "gray");
    }
}
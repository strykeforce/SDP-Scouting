package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchDataAutoMove extends MatchDataView implements IMatchDataView {

    public MatchDataAutoMove(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int sumMove = 0;
        int instances = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            int move = (boolean)data.get("auto-drive")  ? 1 : 0;
            sumMove += move;
            instances ++;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double percentage = (sumMove / instances);
            setValueText(formatPercentageAsString(percentage), "gray");
        }
    }
}

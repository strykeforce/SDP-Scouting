package org.wildstang.wildrank.androidv2.views.data.crescendo.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataTeleMadeAmp extends MatchDataView implements IMatchDataView {

    public MatchDataTeleMadeAmp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    @Override
    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("tele_made_amp") == null) {

            setValueText("Null","gray");
            return;
        }
        int madeAmp = (int) data.get("tele_made_amp");
        didSomething = true;
        setValueText(formatNumberAsString(madeAmp), "gray");
    }
}

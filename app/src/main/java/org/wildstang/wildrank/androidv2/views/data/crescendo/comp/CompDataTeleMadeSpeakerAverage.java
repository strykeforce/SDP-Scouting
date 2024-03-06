package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleMadeSpeakerAverage extends MatchDataView implements IMatchDataView {
    public CompDataTeleMadeSpeakerAverage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int speaker = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_made_speaker") == null) {
                return;
            }
            speaker += (int) data.get("tele_made_speaker");
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double average = (double) speaker / (double) documents.size();
            setValueText(formatNumberAsString(average), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

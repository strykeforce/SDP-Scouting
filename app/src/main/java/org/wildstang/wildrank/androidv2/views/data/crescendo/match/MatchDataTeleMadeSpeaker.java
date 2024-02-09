package org.wildstang.wildrank.androidv2.views.data.crescendo.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataTeleMadeSpeaker extends MatchDataView implements IMatchDataView {

    public MatchDataTeleMadeSpeaker(Context context, AttributeSet attrs) {
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
        if (data.get("tele_made_speaker") == null && data.get("tele_missed_speaker") == null ) {
            return;
        }
        int madeSpeaker = (int) data.get("tele_made_speaker");
        int missedSpeaker = (int) data.get("tele_missed_speaker");

        int totalSpeaker = madeSpeaker + missedSpeaker;

        double percentageMade = 0.0;
        if (totalSpeaker > 0) {
            percentageMade = (double) madeSpeaker / totalSpeaker * 100;
        }else {
            percentageMade = (double) 0.0;
        }
        String SpeakerData = String.format("%d/%d -> (%.1f%%)", madeSpeaker, totalSpeaker, percentageMade);


        didSomething = true;
        setValueText((SpeakerData), "gray");
    }
}

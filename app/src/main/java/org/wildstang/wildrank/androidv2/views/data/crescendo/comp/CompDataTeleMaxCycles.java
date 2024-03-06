package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleMaxCycles extends MatchDataView implements IMatchDataView {
    public CompDataTeleMaxCycles(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        ;
        int AmpMax = 0;
        int TrapMax = 0;
        int CycleMax = 0;

        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_made_speaker") == null && data.get("tele_missed_speaker")== null) {
                return;
            }
            // find match with max speaker cycles

            int MadeSpeaker = (int) data.get("tele_made_speaker");
            int MissedSpeaker = (int) data.get("tele_missed_speaker");
            int SpeakerMax = MadeSpeaker+MissedSpeaker;
            //Find max amp Cycles

            if ( data.get("tele_made_amp") == null) {
            return;
            }
            int MaxAmp = (int) data.get("tele_made_amp");

            // Find Max Trap
            if (data.get("tele_traps")==null){
                return;
            }
            int MaxTrap = (int) data.get("tele_traps");

            int Cycles = SpeakerMax+MaxTrap+MaxAmp;
            if(CycleMax < Cycles ){
                CycleMax = Cycles;
            }

            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(formatNumberAsString(CycleMax), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

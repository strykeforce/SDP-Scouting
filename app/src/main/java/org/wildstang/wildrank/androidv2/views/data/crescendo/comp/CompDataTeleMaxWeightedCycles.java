package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleMaxWeightedCycles extends MatchDataView implements IMatchDataView {
    public CompDataTeleMaxWeightedCycles(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"

        double AmpMax = 0;
        double TrapMax = 0;
        double CycleMax = 0;

        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_made_speaker") == null && data.get("tele_missed_speaker")== null) {
                return;
            }
            // find match with max speaker cycles

            double MadeSpeaker = Double.parseDouble(Integer.toString((int) data.get("tele_made_speaker")));
            double MissedSpeaker = Double.parseDouble(Integer.toString((int) data.get("tele_missed_speaker")));
            double SpeakerMax = MadeSpeaker+MissedSpeaker;
            //Find max amp Cycles

            if ( data.get("tele_made_amp") == null) {
            return;
            }
            double MaxAmp = Double.parseDouble(Integer.toString((int) data.get("tele_made_amp"))) * 1.25;

            // Find Max Trap
            if (data.get("tele_traps")==null){
                return;
            }
            double MaxTrap = Double.parseDouble(Integer.toString((int) data.get("tele_traps"))) * 2.0;

            double Cycles = SpeakerMax+MaxTrap+MaxAmp;
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

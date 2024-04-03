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
        double MaxCycles = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("tele_made_speaker") == null) {
                return;
            }
            if (data.get("tele_missed_speaker") == null) {
                return;
            }
            if (data.get("tele_made_amp") == null) {
                return;
            }
            if (data.get("tele_passes") == null) {
                return;
            }
            if (data.get("Trap") == null) {
                return;
            }
            double Cycles = 0;
            Cycles += Double.parseDouble(Integer.toString((int) data.get("tele_made_speaker")));
            Cycles += Double.parseDouble(Integer.toString((int) data.get("tele_missed_speaker")));
            Cycles += Double.parseDouble(Integer.toString((int) data.get("tele_made_amp"))) * 1.25;
            Cycles += Double.parseDouble(Integer.toString((int) data.get("tele_passes"))) * 0.6;
            Cycles += Double.parseDouble(((String) data.get("Trap")).substring(2, 3)) * 2.0;
            if (MaxCycles < Cycles) {
                MaxCycles = Cycles;
            }
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(formatNumberAsString(MaxCycles), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

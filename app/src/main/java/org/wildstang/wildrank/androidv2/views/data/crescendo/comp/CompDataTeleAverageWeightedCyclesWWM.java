package org.wildstang.wildrank.androidv2.views.data.crescendo.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleAverageWeightedCyclesWWM extends MatchDataView implements IMatchDataView {
    public CompDataTeleAverageWeightedCyclesWWM(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        double Cycles = 0;
        double worst = -1;
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
            double amount = 0;
            amount += Double.parseDouble(Integer.toString((int) data.get("tele_made_speaker")));
            amount += Double.parseDouble(Integer.toString((int) data.get("tele_missed_speaker")));
            amount += Double.parseDouble(Integer.toString((int) data.get("tele_made_amp"))) * 1.25;
            amount += Double.parseDouble(Integer.toString((int) data.get("tele_passes"))) * 0.6;
            amount += Double.parseDouble(((String) data.get("Trap")).substring(2, 3)) * 2.0;
            if (worst == -1) {
                worst = amount;
            } else if (worst > amount) {
                worst = amount;
            }
            Cycles += amount;
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double average = (Cycles - worst) / ((double) documents.size() - 1);
            setValueText(formatNumberAsString(average), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

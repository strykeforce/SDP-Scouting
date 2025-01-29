package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataTeleHumanNetAlgaeAverage extends MatchDataView implements IMatchDataView {
    public CompDataTeleHumanNetAlgaeAverage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        int humanNet = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("human_number") == null || data.get("tele_human_net") == null) {
                continue;
            }
            System.out.println("Number: " + data.get("human_number") + "\nDocument Team Number: " + document.getProperty("team_number")); //FIXME
            if (data.get("human_number") == document.getProperty("key")) {
                humanNet += (int) data.get("tele_human_net");
            }
        }
        double average = (double) humanNet / (double) documents.size();
        setValueText(formatNumberAsString(average), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

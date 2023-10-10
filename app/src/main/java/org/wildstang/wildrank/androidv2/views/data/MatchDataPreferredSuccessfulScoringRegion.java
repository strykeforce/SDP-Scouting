package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchDataPreferredSuccessfulScoringRegion extends MatchDataView implements IMatchDataView {

    public MatchDataPreferredSuccessfulScoringRegion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static String mostCommonElement(List<String> list) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        for(int i=0; i< list.size(); i++) {

            Integer frequency = map.get(list.get(i));
            if(frequency == null) {
                map.put(list.get(i), 1);
            } else {
                map.put(list.get(i), frequency+1);
            }
        }

        String mostCommonKey = null;
        int maxValue = -1;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {

            if(entry.getValue() > maxValue) {
                mostCommonKey = entry.getKey();
                maxValue = entry.getValue();
            }
        }

        return mostCommonKey;
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        double outerScores = 0;
        double innerScores = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null || data.get("cycles") == null) {
                return;
            }
            List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("cycles");
            ///String alliance = (String) /// TODO: grab the alliance so we can get their scoring locs transformed
            for (Map<String, Object> cycle : cycles) {
                boolean loc1 = (boolean) cycle.get(CycleModel.LOCATION_1_KEY);
                boolean loc2 = (boolean) cycle.get(CycleModel.LOCATION_2_KEY);
                boolean loc3 = (boolean) cycle.get(CycleModel.LOCATION_3_KEY);
                boolean loc4 = (boolean) cycle.get(CycleModel.LOCATION_4_KEY);
                boolean loc5 = (boolean) cycle.get(CycleModel.LOCATION_5_KEY);
                boolean loc6 = (boolean) cycle.get(CycleModel.LOCATION_6_KEY);
                boolean loc7 = (boolean) cycle.get(CycleModel.LOCATION_7_KEY);
                boolean loc8 = (boolean) cycle.get(CycleModel.LOCATION_8_KEY);
                //boolean lowerTarget = (boolean) cycle.get(CycleModel.HUB_LOWER_SCORE_KEY);
                boolean upperMiss = (boolean) cycle.get(CycleModel.HUB_UPPER_MISS_KEY);
                boolean lowerMiss = (boolean) cycle.get(CycleModel.HUB_LOWER_MISS_KEY);
                if ((!upperMiss || !lowerMiss) && (loc1 || loc2 || loc3 || loc4 )) {
                    innerScores++;
                }

                if ((!upperMiss || !lowerMiss) && (loc5 || loc6 || loc7 || loc8 )) {
                    outerScores++;
                }
            }
        }
        if (innerScores + outerScores == 0) {
            setValueText("N/A", "gray");
        } else if (innerScores < outerScores) {
            double percentage = (outerScores/ (innerScores + outerScores));
            setValueText("Outer is more common: " + formatPercentageAsString(percentage), "gray");
        } else if (innerScores > outerScores){
            double percentage = (innerScores/ (innerScores + outerScores));
            setValueText("Inner is more common: " + formatPercentageAsString(percentage), "gray");
        } else if (innerScores == outerScores){
            setValueText("Equally as likely", "gray");

        }
    }

    public void calculateFromDocument(Document document) {}
}

package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.List;
import java.util.Map;

public class MatchDataPercentageGamePiecesScoredView extends MatchDataView implements IMatchDataView {

    public MatchDataPercentageGamePiecesScoredView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        double totalAttempts = 0;
        double totalScores = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null || data.get("cycles") == null) {
                return;
            }
            List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("cycles");
            for (Map<String, Object> cycle : cycles) {
                boolean upperScore = (boolean) cycle.get(CycleModel.HUB_UPPER_SCORE_KEY);
                boolean lowerScore = (boolean) cycle.get(CycleModel.HUB_LOWER_SCORE_KEY);
                boolean upperMiss = (boolean) cycle.get(CycleModel.HUB_UPPER_MISS_KEY);
                boolean lowerMiss = (boolean) cycle.get(CycleModel.HUB_LOWER_MISS_KEY);
                if ((upperScore || lowerScore) && !upperMiss && !lowerMiss) {
                    totalScores++;
                }

                if (upperScore || upperMiss || lowerScore || lowerMiss) {
                    totalAttempts++;
                }
            }
        }
        if (totalAttempts == 0) {
            setValueText("N/A", "gray");
        } else {
            double percentage = (totalScores / totalAttempts);
            setValueText(formatPercentageAsString(percentage), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

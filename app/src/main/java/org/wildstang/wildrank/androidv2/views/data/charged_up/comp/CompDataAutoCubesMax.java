package org.wildstang.wildrank.androidv2.views.data.charged_up.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class CompDataAutoCubesMax extends MatchDataView implements IMatchDataView {
    public CompDataAutoCubesMax(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int max = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("auto_top_cubes") == null || data.get("auto_middle_cubes") == null || data.get("auto_bottom_cubes") == null) {
                return;
            }
            int cubes = 0;
            cubes += (int) data.get("auto_top_cubes");
            cubes += (int) data.get("auto_middle_cubes");
            cubes += (int) data.get("auto_bottom_cubes");
            if (cubes > max) {
                max = cubes;
            }
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(formatNumberAsString(max), "gray");
        }
    }

    public void calculateFromDocument(Document document) {}
}

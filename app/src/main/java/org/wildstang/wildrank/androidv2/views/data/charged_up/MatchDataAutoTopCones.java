package org.wildstang.wildrank.androidv2.views.data.charged_up;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MatchDataAutoTopCones extends MatchDataView implements IMatchDataView {

    public MatchDataAutoTopCones(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private String matchKey;
    private String teamKey;

    public MatchDataAutoTopCones(Context context, AttributeSet attrs, String matchKey, String teamKey) {
        super(context, attrs);
        this.matchKey = matchKey;
        this.teamKey = teamKey;
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int autoTopCones = 0;
        Document doc;
        try {
            doc = DatabaseManager.getInstance(this.getContext()).getMatchResults(matchKey, teamKey);
        } catch (CouchbaseLiteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> data = (Map<String, Object>) doc.getProperty("data");
        if (data == null) {
            return;
        }
        autoTopCones = (int) data.get("auto_top_cones");
        didSomething = true;
        setValueText("" + formatNumberAsString(autoTopCones), "gray");
    }
}

package org.wildstang.wildrank.androidv2.views.data.crescendo.match;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataEndHighNotes extends MatchDataView implements IMatchDataView {

    public MatchDataEndHighNotes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}


    public void calculateFromDocument(Document document) {
        if (document == null || document.getProperty("data") == null) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
        if (data.get("HighNotes") == null) {
            return;
        }
        Object highNotesObject = data.get("HighNotes");
        String highNotesString = (String) highNotesObject;
                //trim tabs
        highNotesString = highNotesString.trim();
        // Change String to Integer
        int highNotes = Integer.parseInt(highNotesString);
        didSomething = true;
        setValueText(formatNumberAsString(highNotes), "gray");


    }
}
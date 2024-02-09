package org.wildstang.wildrank.androidv2.views.data.crescendo.match;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchDataAutoPath extends MatchDataView implements IMatchDataView {

    public MatchDataAutoPath(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {}

    public void calculateFromDocument(Document document) {

        System.out.println("\nMatchDataAutoPath is running\n");

        if (document == null || document.getProperty("data") == null) {
            return;
        }

        System.out.println("\nInitial null checks fine\n");

        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

        System.out.println("\nCheck out this data:\n");
        System.out.println(data);

        ArrayList<Pair<String, Long>> presses = new ArrayList<Pair<String, Long>>();
        for (int i = 1; i < 11; i++) {
            if (data.get("redbutton" + formatNumberAsString(i)) != null) {
                for (int j = 0; j < ((ArrayList<Long>) data.get("redbutton" + formatNumberAsString(i))).size(); j++) {
                    Pair<String, Long> item = new Pair<>("redbutton" + formatNumberAsString(i), (long) ((ArrayList<Long>) data.get("redbutton" + formatNumberAsString(i))).get(j));
                    presses.add(item);
                }
            }
            if (data.get("bluebutton" + formatNumberAsString(i)) != null) {
                for (int j = 0; j < ((ArrayList<Long>) data.get("bluebutton" + formatNumberAsString(i))).size(); j++) {
                    Pair<String, Long> item = new Pair<>("bluebutton" + formatNumberAsString(i), (long) ((ArrayList<Long>) data.get("bluebutton" + formatNumberAsString(i))).get(j));
                    presses.add(item);
                }
            }
        }

        System.out.println("\nWow look at presses:\n");
        System.out.println(presses);

        ArrayList<Pair<String, Long>> pathList = new ArrayList<Pair<String, Long>>();
        for (int t = 0; t < presses.size(); t++) {
            if (pathList.size() == 0) {
                pathList.add(presses.get(t));
            } else {
                for (int f = 0; f < pathList.size(); f++) {
                    if ((long) (((Pair) presses.get(t)).second) < (long) (((Pair) pathList.get(f)).second)) {
                        pathList.add(t, (Pair) presses.get(t));
                    } else if (f == pathList.size() - 1) {
                        pathList.add((Pair) presses.get(t));
                    }
                }
            }
        }

        System.out.println("\nWow look at pathList:\n");
        System.out.println(pathList);

        String path = "";
        for (int d = 0; d < pathList.size(); d++) {
            if ((String) (((Pair) (pathList.get(d))).first) == "redbutton1" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton1") {
                path += "--> Note One ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton2" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton2") {
                path += "--> Note Two ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton3" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton3") {
                path += "--> Note Three ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton4" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton4") {
                path += "--> Note Four ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton5" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton5") {
                path += "--> Note Five ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton6" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton6") {
                path += "--> Note Six ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton7" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton7") {
                path += "--> Note Seven ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton8" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton8") {
                path += "--> Note Eight ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton9" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton9") {
                path += "--> Speaker ";
            } else if ((String) (((Pair) (pathList.get(d))).first) == "redbutton10" || (String) (((Pair) (pathList.get(d))).first) == "bluebutton10") {
                path += "--> Amp ";
            }
        }

        System.out.println("\nWow look at path:\n");
        System.out.println(path);

        setValueText(path, "gray");
    }
}
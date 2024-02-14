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
import java.util.Objects;

public class MatchDataAutoPath extends MatchDataView implements IMatchDataView {

    public MatchDataAutoPath(Context context, AttributeSet attrs) {
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

        System.out.println("\nCheck out this data:\n");
        System.out.println(data);

        ArrayList<Pair<String, Long>> presses = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            if (data.get("redbutton" + i) != null) {
                for (int j = 0; j < ((ArrayList<Long>) data.get("redbutton" + i)).size(); j++) {
                    Pair<String, Long> item = new Pair<>("redbutton" + i, ((ArrayList<Long>) data.get("redbutton" + i)).get(j));
                    presses.add(item);
                    didSomething = true;
                }
            }
            if (data.get("bluebutton" + i) != null) {
                for (int j = 0; j < ((ArrayList<Long>) data.get("bluebutton" + i)).size(); j++) {
                    Pair<String, Long> item = new Pair<>("bluebutton" + i, ((ArrayList<Long>) data.get("bluebutton" + i)).get(j));
                    presses.add(item);
                    didSomething = true;
                }
            }
        }

        System.out.println("\nWow look at presses:\n");
        System.out.println(presses);

        // FIXME freezes some point after here \/

        ArrayList<Pair<String, Long>> pathList = new ArrayList<>();
        for (int t = 0; t < presses.size(); t++) {
            if (pathList.size() == 0) {
                pathList.add(presses.get(t));
            } else {
                for (int f = 0; f < pathList.size(); f++) {
                    if (presses.get(t).second < (long) (pathList.get(f).second)) {
                        pathList.add(t, presses.get(t));
                    } else if (f == pathList.size() - 1) {
                        pathList.add(presses.get(t));
                    }
                }
            }
        }

        System.out.println("\nWow look at pathList:\n");
        System.out.println(pathList);

        // FIXME freezes some point before here /\

        String path = "";
        for (int d = 0; d < pathList.size(); d++) {
            if (Objects.equals(pathList.get(d).first, "redbutton1") || Objects.equals(pathList.get(d).first, "bluebutton1")) {
                path += "--> Note One ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton2") || Objects.equals(pathList.get(d).first, "bluebutton2")) {
                path += "--> Note Two ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton3") || Objects.equals(pathList.get(d).first, "bluebutton3")) {
                path += "--> Note Three ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton4") || Objects.equals(pathList.get(d).first, "bluebutton4")) {
                path += "--> Note Four ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton5") || Objects.equals(pathList.get(d).first, "bluebutton5")) {
                path += "--> Note Five ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton6") || Objects.equals(pathList.get(d).first, "bluebutton6")) {
                path += "--> Note Six ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton7") || Objects.equals(pathList.get(d).first, "bluebutton7")) {
                path += "--> Note Seven ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton8") || Objects.equals(pathList.get(d).first, "bluebutton8")) {
                path += "--> Note Eight ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton9") || Objects.equals(pathList.get(d).first, "bluebutton9")) {
                path += "--> Speaker ";
            } else if (Objects.equals(pathList.get(d).first, "redbutton10") || Objects.equals(pathList.get(d).first, "bluebutton10")) {
                path += "--> Amp ";
            }
        }

        System.out.println("\nWow look at path:\n");
        System.out.println(path);

        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(path, "gray");
        }
    }
}
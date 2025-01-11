package org.wildstang.wildrank.androidv2.views.data.crescendo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AutosDataView extends MatchDataView implements IMatchDataView {
    public AutosDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocument(Document document) {}

    @Override
    public void calculateFromDocuments(List<Document> matchDocs) {
        if (matchDocs == null || matchDocs.isEmpty()) {
            return;
        }
        boolean didSomething = false;
        ArrayList<String> autopaths = new ArrayList<>();
        for (Document doc : matchDocs) {
            Map<String, Object> data = (Map<String, Object>) doc.getProperty("data");
            ArrayList<Pair<String, Long>> presses = new ArrayList<>();
            for (int i = 1; i < 11; i++) {
                if (data.get("redbutton" + i) != null) {
                    for (int j = 0; j < ((ArrayList<Long>) data.get("redbutton" + i)).size(); j++) {
                        Pair<String, Long> item = new Pair<>("redbutton" + i, ((ArrayList<Number>) data.get("redbutton" + i)).get(j).longValue());
                        presses.add(item);
                        didSomething = true;
                    }
                }
                if (data.get("bluebutton" + i) != null) {
                    for (int j = 0; j < ((ArrayList<Long>) data.get("bluebutton" + i)).size(); j++) {
                        Pair<String, Long> item = new Pair<>("bluebutton" + i, ((ArrayList<Number>) data.get("bluebutton" + i)).get(j).longValue());
                        presses.add(item);
                        didSomething = true;
                    }
                }
            }

            ArrayList<Pair<String, Long>> pathList = new ArrayList<>();
            for (int t = 0; t < presses.size(); t++) {
                if (pathList.size() == 0) {
                    pathList.add(presses.get(t));
                } else {
                    for (int f = 0; f < pathList.size(); f++) {
                        if (presses.get(t).second < pathList.get(f).second) {
                            pathList.add(f, presses.get(t));
                            break;
                        } else if (f == pathList.size() - 1) {
                            pathList.add(presses.get(t));
                            break;
                        }
                    }
                }
            }

            String path = "";
            for (int b = 1; b < 4; b++) {
                if (data.get("redposbutton" + b) != null) {
                    if (((ArrayList) data.get("redposbutton" + b)).size() != 0) {
                        if (b == 3) {
                            path += "Started on Source Side ";
                            break;
                        } else if (b == 1) {
                            path += "Started on Amp Side ";
                            break;
                        } else if (b == 2) {
                            path += "Started in Middle ";
                            break;
                        }
                    }
                }
                if (data.get("blueposbutton" + b) != null) {
                    if (((ArrayList) data.get("blueposbutton" + b)).size() != 0) {
                        if (b == 3) {
                            path += "Started on Source Side ";
                            break;
                        } else if (b == 1) {
                            path += "Started on Amp Side ";
                            break;
                        } else if (b == 2) {
                            path += "Started in Middle ";
                            break;
                        }
                    }
                }
            }
            for (int d = 0; d < pathList.size(); d++) {
                if (Objects.equals(pathList.get(d).first, "redbutton1") || Objects.equals(pathList.get(d).first, "bluebutton1")) {
                    path += "--> Shared Note One ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton2") || Objects.equals(pathList.get(d).first, "bluebutton2")) {
                    path += "--> Shared Note Two ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton3") || Objects.equals(pathList.get(d).first, "bluebutton3")) {
                    path += "--> Shared Note Three ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton4") || Objects.equals(pathList.get(d).first, "bluebutton4")) {
                    path += "--> Shared Note Four ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton5") || Objects.equals(pathList.get(d).first, "bluebutton5")) {
                    path += "--> Shared Note Five ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton6")) {
                    path += "--> Red Note One ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton7")) {
                    path += "--> Red Note Two ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton8")) {
                    path += "--> Red Note Three ";
                } else if (Objects.equals(pathList.get(d).first, "bluebutton6")) {
                    path += "--> Blue Note One ";
                } else if (Objects.equals(pathList.get(d).first, "bluebutton7")) {
                    path += "--> Blue Note Two ";
                } else if (Objects.equals(pathList.get(d).first, "bluebutton8")) {
                    path += "--> Blue Note Three ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton9") || Objects.equals(pathList.get(d).first, "bluebutton9")) {
                    path += "--> Scored Speaker ";
                } else if (Objects.equals(pathList.get(d).first, "redbutton10") || Objects.equals(pathList.get(d).first, "bluebutton10")) {
                    path += "--> Missed Speaker ";
                }
            }

            if (autopaths.size() == 0) {
                autopaths.add(path);
            } else {
                for (int b = 0; b < autopaths.size(); b++) {
                    if (path.equals(autopaths.get(b))) {
                        break;
                    } else if (b == autopaths.size() - 1) {
                        autopaths.add(path);
                        break;
                    }
                }
            }
        }
        String output = "";
        for (int p = 0; p < autopaths.size(); p++) {
            if (p != 0) {
                output += "\n\n";
            }
            output += (p + 1) + ": " + autopaths.get(p);
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(output, "gray");
        }
    }
}
package org.wildstang.wildrank.androidv2.views.data.reefscape.comp;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.data.MatchDataView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CompDataTeleHumanNetAlgaeMax extends MatchDataView implements IMatchDataView {
    public CompDataTeleHumanNetAlgaeMax(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }

        List<Document> matchDocuments = new ArrayList<>();
        String teamNumber = documents.get(0).getProperty("team_key").toString().substring(3);

        for (Document document : documents) {
            try {
                if (document.getProperty("alliance").toString().contains("red")) {
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                } else {
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int i = 0;
        int tempHumanNet = 0;
        double max = 0.0;
        int values = 0;
        for (Document document : matchDocuments) {
            i++;
            if (document == null || document.getProperty("data") == null) {
                if (i % 3 == 0) {
                    if (values != 0) {
                        double humanNet = (double) tempHumanNet / (double) values;
                        if (humanNet > max) {
                            max = humanNet;
                        }
                        tempHumanNet = 0;
                        values = 0;
                    }
                }
                continue;
            }
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("human_number") == null || data.get("tele_human_net") == null) {
                if (i % 3 == 0) {
                    if (values != 0) {
                        double humanNet = (double) tempHumanNet / (double) values;
                        if (humanNet > max) {
                            max = humanNet;
                        }
                        tempHumanNet = 0;
                        values = 0;
                    }
                }
                continue;
            }
            if (Objects.equals(data.get("human_number").toString(), teamNumber)) {
                tempHumanNet += (int) data.get("tele_human_net");
                values++;
            }
            if (i % 3 == 0) {
                if (values != 0) {
                    double humanNet = (double) tempHumanNet / (double) values;
                    if (humanNet > max) {
                        max = humanNet;
                    }
                    tempHumanNet = 0;
                    values = 0;
                }
            }
        }
        setValueText(formatNumberAsString(max), "gray");
    }

    public void calculateFromDocument(Document document) {}
}

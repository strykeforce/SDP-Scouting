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

public class CompDataTeleHumanNetAlgaeAverage extends MatchDataView implements IMatchDataView {
    public CompDataTeleHumanNetAlgaeAverage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return;
        }

        List<Document> matchDocuments = new ArrayList<>();
        List<Document> comparisonDocuments = new ArrayList<>();
        String teamNumber = documents.get(0).getProperty("team_key").toString().substring(3);

        for (Document document : documents) {
            try {
                if (document.getProperty("alliance").toString().contains("red")) {
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                } else {
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    comparisonDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getRedTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[0].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[1].toString()));
                    matchDocuments.add((Document) DatabaseManager.getInstance(getContext()).getMatchResults(document.getProperty("match_key").toString(), Utilities.getBlueTeamsFromMatchDocument(DatabaseManager.getInstance(getContext()).getMatchFromKey(document.getProperty("match_key").toString()))[2].toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int i = 0;
        int humanNet = 0;
        int algae = 0;
        double humanNetPercentage = 0;
        int entries = 0;
        int played = 0;
        for (Document document : matchDocuments) {
            i++;
            if (document == null || document.getProperty("data") == null) {
                if (i % 3 == 0) {
                    if (entries != 0) {
                        for (int j = i - 3; j < i; j++) {
                            if (comparisonDocuments.get(j) != null && comparisonDocuments.get(j).getProperty("data") != null) {
                                Map<String, Object> comparisonData = (Map<String, Object>) comparisonDocuments.get(j).getProperty("data");
                                if (comparisonData.get("tele_processor") != null) {
                                    algae += (int) comparisonData.get("tele_processor");
                                }
                            }
                        }
                        double averagedHumanNet = (double) humanNet / (double) entries;
                        if (algae != 0) {
                            humanNetPercentage += averagedHumanNet / (double) algae;
                            played++;
                        }
                        humanNet = 0;
                        algae = 0;
                        entries = 0;
                    }
                }
                continue;
            }
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data.get("human_number") == null || data.get("tele_human_net") == null) {
                if (i % 3 == 0) {
                    if (entries != 0) {
                        for (int j = i - 3; j < i; j++) {
                            if (comparisonDocuments.get(j) != null && comparisonDocuments.get(j).getProperty("data") != null) {
                                Map<String, Object> comparisonData = (Map<String, Object>) comparisonDocuments.get(j).getProperty("data");
                                if (comparisonData.get("tele_processor") != null) {
                                    algae += (int) comparisonData.get("tele_processor");
                                }
                            }
                        }
                        double averagedHumanNet = (double) humanNet / (double) entries;
                        if (algae != 0) {
                            humanNetPercentage += averagedHumanNet / (double) algae;
                            played++;
                        }
                        humanNet = 0;
                        algae = 0;
                        entries = 0;
                    }
                }
                continue;
            }
            if (Objects.equals(data.get("human_number").toString(), teamNumber)) {
                humanNet += (int) data.get("tele_human_net");
                entries++;
            }
            if (i % 3 == 0) {
                if (entries != 0) {
                    for (int j = i - 3; j < i; j++) {
                        if (comparisonDocuments.get(j) != null && comparisonDocuments.get(j).getProperty("data") != null) {
                            Map<String, Object> comparisonData = (Map<String, Object>) comparisonDocuments.get(j).getProperty("data");
                            if (comparisonData.get("tele_processor") != null) {
                                algae += (int) comparisonData.get("tele_processor");
                            }
                        }
                    }
                    double averagedHumanNet = (double) humanNet / (double) entries;
                    if (algae != 0) {
                        humanNetPercentage += averagedHumanNet / (double) algae;
                        played++;
                    }
                    humanNet = 0;
                    algae = 0;
                    entries = 0;
                }
            }
        }

        if (played != 0) {
            double average = humanNetPercentage / (double) played;
            setValueText(formatPercentageAsString(average), "gray");
        } else setValueText("N/A", "gray");
    }

    public void calculateFromDocument(Document document) {}
}

package org.wildstang.wildrank.androidv2.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.couchbase.lite.Document;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.activities.MatchNotesActivity;
import org.wildstang.wildrank.androidv2.views.WhiteboardView;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingSpinnerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamSummariesGraphsFragment extends TeamSummariesFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summaries_graphs, container, false);
    }

    @Override
    public void acceptNewTeamData(String teamKey, Document teamDocument, Document pitDocument, List<Document> matchDocuments) {
        if (matchDocuments == null || matchDocuments.size() == 0) {
            return;
        }

        BarChart chart = (BarChart) getView().findViewById(R.id.chart);
        chart.getAxisRight().setDrawLabels(false);

        ScoutingSpinnerView spinner = (ScoutingSpinnerView) getView().findViewById(R.id.chart_spinner);

        Button button = (Button) getView().findViewById(R.id.chart_button);

        button.setOnClickListener(v -> {
            ArrayList<BarEntry> entries = new ArrayList<>();
            List<Pair<String, Float>> xValues = new ArrayList<>();
            ArrayList<Float> max = new ArrayList<>();

            if (spinner.getSelectedItem().equals("Cycles")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
                    int cycles = 0;
                    cycles += (int) data.get("tele_made_speaker");
                    cycles += (int) data.get("tele_missed_speaker");
                    cycles += (int) data.get("tele_made_amp");
                    cycles += (int) data.get("tele_traps");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) cycles);

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) cycles));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) cycles));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) cycles));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Weighted Cycles")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
                    double cycles = 0.0;
                    cycles += Double.valueOf(Integer.toString((int) data.get("tele_made_speaker")));
                    cycles += Double.valueOf(Integer.toString((int) data.get("tele_missed_speaker")));
                    cycles += Double.valueOf(Integer.toString((int) data.get("tele_made_amp"))) * 1.25;
                    cycles += Double.valueOf(Integer.toString((int) data.get("tele_traps"))) * 2;

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) cycles);

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) cycles));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) cycles));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) cycles));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Amp and Speaker")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) ((int) data.get("tele_made_amp") + (int) data.get("tele_made_speaker")));

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) ((int) data.get("tele_made_amp") + (int) data.get("tele_made_speaker"))));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) ((int) data.get("tele_made_amp") + (int) data.get("tele_made_speaker"))));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) ((int) data.get("tele_made_amp") + (int) data.get("tele_made_speaker"))));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Amp")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) (int) data.get("tele_made_amp"));

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_amp")));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_amp")));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_amp")));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Speaker")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) (int) data.get("tele_made_speaker"));

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_speaker")));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_speaker")));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) (int) data.get("tele_made_speaker")));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Auto")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
                    int notes = 0;
                    for (int i = 1; i < 11; i++) {
                        if (data.get("redbutton" + i) != null) {
                            for (int j = 0; j < ((ArrayList<Long>) data.get("redbutton" + i)).size(); j++) {
                                notes++;
                            }
                        }
                        if (data.get("bluebutton" + i) != null) {
                            for (int j = 0; j < ((ArrayList<Long>) data.get("bluebutton" + i)).size(); j++) {
                                notes++;
                            }
                        }
                    }

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) notes);

                    if (xValues.size() == 0) {
                        xValues.add(Pair.create(mNum.substring(start), (float) notes));
                    } else {
                        for (int k = 0; k < xValues.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf((xValues.get(k)).first)) {
                                xValues.add(k, Pair.create(mNum.substring(start), (float) notes));
                                break;
                            } else if (k == xValues.size() - 1) {
                                xValues.add(Pair.create(mNum.substring(start), (float) notes));
                                break;
                            }
                        }
                    }
                }
            }

            float lineMax = 0f;
            for (int j = 0; j < max.size(); j++) {
                if (max.get(j) > lineMax) {
                    lineMax = max.get(j);
                }
            }
            float increase = lineMax % 5;
            lineMax += (5 - increase);

            ArrayList<String> format = new ArrayList<>();
            for (int d = 0; d < xValues.size(); d++) {
                entries.add(new BarEntry(d + 1, xValues.get(d).second));
                format.add(Integer.toString(d + 1));
            }

            YAxis yAxis = chart.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(lineMax);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount((int) (lineMax / 5));

            BarDataSet dataSet = new BarDataSet(entries, spinner.getSelectedItem().toString());
            dataSet.setColors(Color.BLACK);
            BarData data = new BarData(dataSet);
            chart.setData(data);
            chart.getDescription().setEnabled(false);
            chart.invalidate();

            chart.getXAxis().setDrawLabels(true);
            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(format));
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getXAxis().setGranularity(1f);
            chart.getXAxis().setGranularityEnabled(true);
            chart.getXAxis().setLabelCount(1);
        });
    }
}
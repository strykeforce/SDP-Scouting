package org.wildstang.wildrank.androidv2.fragments.TeamSummaries;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.couchbase.lite.Document;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.fragments.TeamSummaries.TeamSummariesFragment;
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
            ArrayList<Float> barValues = new ArrayList<>();
            ArrayList<float[]> coralBarValues = new ArrayList<>();
            ArrayList<String> xAxisLabels = new ArrayList<>();
            ArrayList<Float> max = new ArrayList<>();

            if (spinner.getSelectedItem().equals("Removed Algae")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

                    int algae = 0;
                    algae += (int) data.get("auto_upper_removed");
                    algae += (int) data.get("auto_lower_removed");
                    algae += (int) data.get("tele_upper_removed");
                    algae += (int) data.get("tele_lower_removed");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) algae);

                    if (xAxisLabels.size() == 0) {
                        barValues.add((float) algae);
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                barValues.add(k, (float) algae);
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                barValues.add((float) algae);
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Processor")) {
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

                    max.add((float) (int) data.get("tele_processor"));

                    if (xAxisLabels.size() == 0) {
                        barValues.add((float) (int) data.get("tele_processor"));
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                barValues.add(k, (float) (int) data.get("tele_processor"));
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                barValues.add((float) (int) data.get("tele_processor"));
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Net")) {
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

                    max.add((float) (int) data.get("tele_robot_net"));

                    if (xAxisLabels.size() == 0) {
                        barValues.add((float) (int) data.get("tele_robot_net"));
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                barValues.add(k, (float) (int) data.get("tele_robot_net"));
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                barValues.add((float) (int) data.get("tele_robot_net"));
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Auto Coral")) {
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

                    max.add((float) ((int) data.get("auto_level_one") + (int) data.get("auto_level_two") + (int) data.get("auto_level_three") + (int) data.get("auto_level_four")));

                    if (xAxisLabels.size() == 0) {
                        coralBarValues.add(new float[] {(float) (int) data.get("auto_level_one"), (float) (int) data.get("auto_level_two"), (float) (int) data.get("auto_level_three"), (float) (int) data.get("auto_level_four")});
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                coralBarValues.add(k, new float[] {(float) (int) data.get("auto_level_one"), (float) (int) data.get("auto_level_two"), (float) (int) data.get("auto_level_three"), (float) (int) data.get("auto_level_four")});
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                coralBarValues.add(new float[] {(float) (int) data.get("auto_level_one"), (float) (int) data.get("auto_level_two"), (float) (int) data.get("auto_level_three"), (float) (int) data.get("auto_level_four")});
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Tele Coral")) {
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

                    max.add((float) ((int) data.get("tele_level_one") + (int) data.get("tele_level_two") + (int) data.get("tele_level_three") + (int) data.get("tele_level_four")));

                    if (xAxisLabels.size() == 0) {
                        coralBarValues.add(new float[] {(float) (int) data.get("tele_level_one"), (float) (int) data.get("tele_level_two"), (float) (int) data.get("tele_level_three"), (float) (int) data.get("tele_level_four")});
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                coralBarValues.add(k, new float[] {(float) (int) data.get("tele_level_one"), (float) (int) data.get("tele_level_two"), (float) (int) data.get("tele_level_three"), (float) (int) data.get("tele_level_four")});
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                coralBarValues.add(new float[] {(float) (int) data.get("tele_level_one"), (float) (int) data.get("tele_level_two"), (float) (int) data.get("tele_level_three"), (int) data.get("tele_level_four")});
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Combined Coral")) {
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

                    max.add((float) ((int) data.get("auto_level_one") + (int) data.get("auto_level_two") + (int) data.get("auto_level_three") + (int) data.get("auto_level_four") + (int) data.get("tele_level_one") + (int) data.get("tele_level_two") + (int) data.get("tele_level_three") + (int) data.get("tele_level_four")));

                    if (xAxisLabels.size() == 0) {
                        coralBarValues.add(new float[] {(float) ((int) data.get("auto_level_one") + (int) data.get("tele_level_one")), (float) ((int) data.get("auto_level_two") + (int) data.get("tele_level_two")), (float) ((int) data.get("auto_level_three") + (int) data.get("tele_level_three")), (float) ((int) data.get("auto_level_four") + (int) data.get("tele_level_four"))});
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                coralBarValues.add(k, new float[] {(float) ((int) data.get("auto_level_one") + (int) data.get("tele_level_one")), (float) ((int) data.get("auto_level_two") + (int) data.get("tele_level_two")), (float) ((int) data.get("auto_level_three") + (int) data.get("tele_level_three")), (float) ((int) data.get("auto_level_four") + (int) data.get("tele_level_four"))});
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                coralBarValues.add(new float[] {(float) ((int) data.get("auto_level_one") + (int) data.get("tele_level_one")), (float) ((int) data.get("auto_level_two") + (int) data.get("tele_level_two")), (float) ((int) data.get("auto_level_three") + (int) data.get("tele_level_three")), (float) ((int) data.get("auto_level_four") + (int) data.get("tele_level_four"))});
                                xAxisLabels.add(mNum.substring(start));
                                break;
                            }
                        }
                    }
                }
            } else if (spinner.getSelectedItem().equals("Barge")) {
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

                    if (data.get("barge").equals("Parked")) {
                        max.add(2f);
                    } else if (data.get("barge").equals("Shallow Cage (High)")) {
                        max.add(6f);
                    } else if (data.get("barge").equals("Deep Cage (Low)")) {
                        max.add(12f);
                    }

                    if (xAxisLabels.size() == 0) {
                        if (data.get("barge").equals("Not Parked")) {
                            barValues.add(0f);
                            xAxisLabels.add(mNum.substring(start));
                        } else if (data.get("barge").equals("Parked")) {
                            barValues.add(2f);
                            xAxisLabels.add(mNum.substring(start));
                        } else if (data.get("barge").equals("Shallow Cage (High)")) {
                            barValues.add(6f);
                            xAxisLabels.add(mNum.substring(start));
                        } else if (data.get("barge").equals("Deep Cage (Low)")) {
                            barValues.add(12f);
                            xAxisLabels.add(mNum.substring(start));
                        }
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                if (data.get("barge").equals("Not Parked")) {
                                    barValues.add(k, 0f);
                                    xAxisLabels.add(k, mNum.substring(start));
                                } else if (data.get("barge").equals("Parked")) {
                                    barValues.add(k, 2f);
                                    xAxisLabels.add(k, mNum.substring(start));
                                } else if (data.get("barge").equals("Shallow Cage (High)")) {
                                    barValues.add(k, 6f);
                                    xAxisLabels.add(k, mNum.substring(start));
                                } else if (data.get("barge").equals("Deep Cage (Low)")) {
                                    barValues.add(k, 12f);
                                    xAxisLabels.add(k, mNum.substring(start));
                                }
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                if (data.get("barge").equals("Not Parked")) {
                                    barValues.add(0f);
                                    xAxisLabels.add(mNum.substring(start));
                                } else if (data.get("barge").equals("Parked")) {
                                    barValues.add(2f);
                                    xAxisLabels.add(mNum.substring(start));
                                } else if (data.get("barge").equals("Shallow Cage (High)")) {
                                    barValues.add(6f);
                                    xAxisLabels.add(mNum.substring(start));
                                } else if (data.get("barge").equals("Deep Cage (Low)")) {
                                    barValues.add(12f);
                                    xAxisLabels.add(mNum.substring(start));
                                }
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

            if (barValues.size() != 0) {
                for (int d = 0; d < barValues.size(); d++) {
                    entries.add(new BarEntry(d, barValues.get(d)));
                }
            } else if (coralBarValues.size() != 0) {
                for (int b = 0; b < coralBarValues.size(); b++) {
                    entries.add(new BarEntry(b, coralBarValues.get(b)));
                }
            }

            YAxis yAxis = chart.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(lineMax);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount((int) (lineMax / 5));

            BarDataSet dataSet = new BarDataSet(entries, spinner.getSelectedItem().toString());;
            if (barValues.size() != 0) {
                dataSet.setColors(Color.BLACK);
            } else if (coralBarValues.size() != 0) {
                dataSet.setColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
                dataSet.setLabel("");
                dataSet.setStackLabels(new String[] {"Level One", "Level Two", "Level Three", "Level Four"});
            }
            BarData data = new BarData(dataSet);
            chart.setData(data);
            chart.getDescription().setEnabled(false);
            chart.invalidate();

            chart.getXAxis().setDrawLabels(true);
            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getXAxis().setGranularity(1f);
            chart.getXAxis().setDrawGridLines(false);
            chart.getXAxis().setLabelCount(xAxisLabels.size() + 1);

            chart.getAxisLeft().setDrawGridLines(false);
            chart.getAxisRight().setDrawGridLines(false);
        });
    }
}
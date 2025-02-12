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
            } else if (spinner.getSelectedItem().equals("Coral")) {
                for (Document document : matchDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");

                    int coral = 0;
                    coral += (int) data.get("auto_level_one");
                    coral += (int) data.get("auto_level_two");
                    coral += (int) data.get("auto_level_three");
                    coral += (int) data.get("auto_level_four");
                    coral += (int) data.get("tele_level_one");
                    coral += (int) data.get("tele_level_two");
                    coral += (int) data.get("tele_level_three");
                    coral += (int) data.get("tele_level_four");

                    String mNum = (String) document.getProperty("match_key");
                    int start = 0;
                    for (int i = mNum.length() - 1; i >= 0; i--) {
                        if (mNum.charAt(i) == 'm') {
                            start = i + 1;
                            break;
                        }
                    }

                    max.add((float) coral);

                    if (xAxisLabels.size() == 0) {
                        barValues.add((float) coral);
                        xAxisLabels.add(mNum.substring(start));
                    } else {
                        for (int k = 0; k < xAxisLabels.size(); k++) {
                            if (Integer.valueOf(mNum.substring(start)) < Integer.valueOf(xAxisLabels.get(k))) {
                                barValues.add(k, (float) coral);
                                xAxisLabels.add(k, mNum.substring(start));
                                break;
                            } else if (k == xAxisLabels.size() - 1) {
                                barValues.add((float) coral);
                                xAxisLabels.add(mNum.substring(start));
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

            for (int d = 0; d < barValues.size(); d++) {
                entries.add(new BarEntry(d, barValues.get(d)));
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
            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getXAxis().setGranularity(1f);
            chart.getXAxis().setGranularityEnabled(true);
            chart.getXAxis().setLabelCount(xAxisLabels.size() + 1);
        });
    }
}
package org.wildstang.wildrank.androidv2.fragments.TeamsComparison;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingSpinnerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeamsComparisonMaxCyclesFragment extends TeamsComparisonFragment {
    List<List<Document>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comparison_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        acceptNewData(data);
    }

    @Override
    public void acceptNewData(List<List<Document>> allMatchDocuments) {
        data = allMatchDocuments;

        if (allMatchDocuments == null || allMatchDocuments.size() == 0 || getView() == null) {
            return;
        }

        BarChart chart = (BarChart) getView().findViewById(R.id.chart);
        chart.getAxisRight().setDrawLabels(false);

        ScoutingSpinnerView spinner = (ScoutingSpinnerView) getView().findViewById(R.id.sort_spinner);

        Button button = (Button) getView().findViewById(R.id.chart_button);

        button.setOnClickListener(v -> {
            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<Float> barValues = new ArrayList<>();
            ArrayList<String> xAxisLabels = new ArrayList<>();
            ArrayList<Float> max = new ArrayList<>();

            ArrayList<Float> teams = new ArrayList<>();
            try {
                DatabaseManager db = DatabaseManager.getInstance(getActivity());
                Query query = db.getAllTeams();
                QueryEnumerator enumerator = query.run();
                for (Iterator<QueryRow> it = enumerator; it.hasNext();) {
                    teams.add(Float.valueOf(it.next().getKey().toString()));
                }
            } catch (CouchbaseLiteException | IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error loading data. Check LogCat.", Toast.LENGTH_LONG).show();
            }

            for (int i = 0; i < teams.size(); i++) {
                List<Document> teamDocuments = allMatchDocuments.get(i);

                if (teamDocuments == null) break;

                int maxCycles = 0;
                for (Document document : teamDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
                    if (data.get("tele_made_speaker") == null) {
                        return;
                    }
                    if (data.get("tele_missed_speaker") == null) {
                        return;
                    }
                    if (data.get("tele_made_amp") == null) {
                        return;
                    }
                    if (data.get("tele_passes") == null) {
                        return;
                    }
                    if (data.get("Trap") == null) {
                        return;
                    }
                    int Cycles = 0;
                    Cycles += (int) data.get("tele_made_speaker");
                    Cycles += (int) data.get("tele_missed_speaker");
                    Cycles += (int) data.get("tele_made_amp");
                    Cycles += (int) data.get("tele_passes");
                    Cycles += Integer.valueOf(((String) data.get("Trap")).substring(2, 3));
                    if (maxCycles < Cycles) {
                        maxCycles = Cycles;
                    }
                }

                if (spinner.getSelectedItem().equals("Team Number")) {
                    barValues.add((float) maxCycles);
                    xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                } else if (spinner.getSelectedItem().equals("Descending")) {
                    if (barValues.size() == 0) {
                        barValues.add((float) maxCycles);
                        xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                    } else {
                        for (int m = 0; m < barValues.size(); m++) {
                            if ((float) maxCycles >= barValues.get(m)) {
                                barValues.add(m, (float) maxCycles);
                                xAxisLabels.add(m, teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            } else if (m == barValues.size() - 1) {
                                barValues.add((float) maxCycles);
                                xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            }
                        }
                    }
                } else if (spinner.getSelectedItem().equals("Ascending")) {
                    if (barValues.size() == 0) {
                        barValues.add((float) maxCycles);
                        xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                    } else {
                        for (int n = 0; n < barValues.size(); n++) {
                            if ((float) maxCycles <= barValues.get(n)) {
                                barValues.add(n, (float) maxCycles);
                                xAxisLabels.add(n, teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            } else if (n == barValues.size() - 1) {
                                barValues.add((float) maxCycles);
                                xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            }
                        }
                    }
                }
                max.add((float) maxCycles);
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

            BarDataSet dataSet = new BarDataSet(entries, "Max Cycles");
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

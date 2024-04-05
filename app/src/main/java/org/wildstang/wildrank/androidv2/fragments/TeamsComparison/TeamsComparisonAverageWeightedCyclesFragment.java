package org.wildstang.wildrank.androidv2.fragments.TeamsComparison;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeamsComparisonAverageWeightedCyclesFragment extends TeamsComparisonFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comparison_chart, container, false);
    }

    @Override
    public void acceptNewData(List<List<Document>> allMatchDocuments) {
        if (allMatchDocuments == null || allMatchDocuments.size() == 0) {
            return;
        }

        System.out.println(getView());
        BarChart chart = (BarChart) getView().findViewById(R.id.chart);
        chart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Pair<Float, Float>> barValues = new ArrayList<>();
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

            double cycles = 0.0;
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
                cycles += Double.valueOf(Integer.toString((int) data.get("tele_made_speaker")));
                cycles += Double.valueOf(Integer.toString((int) data.get("tele_missed_speaker")));
                cycles += Double.valueOf(Integer.toString((int) data.get("tele_made_amp"))) * 1.25;
                cycles += Double.valueOf(Integer.toString((int) data.get("tele_passes"))) * 0.6;
                cycles += Double.valueOf(((String) data.get("Trap")).substring(2, 3)) * 2.0;
            }

            float average = (float) cycles / (float) teamDocuments.size();
            barValues.add(Pair.create((float) (i), average));
            xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
            max.add((float) average);
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
            entries.add(new BarEntry(barValues.get(d).first, barValues.get(d).second));
        }

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(lineMax);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount((int) (lineMax / 5));

        BarDataSet dataSet = new BarDataSet(entries, "Average Weighted Cycles");
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

    }
}

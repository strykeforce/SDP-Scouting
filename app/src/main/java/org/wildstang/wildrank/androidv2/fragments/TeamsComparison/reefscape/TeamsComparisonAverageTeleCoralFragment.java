package org.wildstang.wildrank.androidv2.fragments.TeamsComparison.reefscape;

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
import org.wildstang.wildrank.androidv2.fragments.TeamsComparison.TeamsComparisonFragment;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingSpinnerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeamsComparisonAverageTeleCoralFragment extends TeamsComparisonFragment {
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
            ArrayList<float[]> barValues = new ArrayList<>();
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

                if (teamDocuments == null) continue;

                int coral = 0;
                float levelOnePercentage = 0;
                float levelTwoPercentage = 0;
                float levelThreePercentage = 0;
                float levelFourPercentage = 0;
                for (Document document : teamDocuments) {
                    Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
                    if (data.get("tele_level_one") == null || data.get("tele_level_two") == null || data.get("tele_level_three") == null || data.get("tele_level_four") == null) {
                        continue;
                    }

                    int match = 0;
                    match += (int) data.get("tele_level_one");
                    match += (int) data.get("tele_level_two");
                    match += (int) data.get("tele_level_three");
                    match += (int) data.get("tele_level_four");
                    coral += match;

                    if (match != 0) {
                        levelOnePercentage += (float) (int) data.get("tele_level_one") / (float) match;
                        levelTwoPercentage += (float) (int) data.get("tele_level_two") / (float) match;
                        levelThreePercentage += (float) (int) data.get("tele_level_three") / (float) match;
                        levelFourPercentage += (float) (int) data.get("tele_level_four") / (float) match;
                    }
                }

                float averageCoral = (float) coral / (float) teamDocuments.size();
                float averageLevelOnePercentage = levelOnePercentage / (float) teamDocuments.size();
                float averageLevelTwoPercentage = levelTwoPercentage / (float) teamDocuments.size();
                float averageLevelThreePercentage = levelThreePercentage / (float) teamDocuments.size();
                float averageLevelFourPercentage = levelFourPercentage / (float) teamDocuments.size();

                float averageLevelOne = averageLevelOnePercentage * averageCoral;
                float averageLevelTwo = averageLevelTwoPercentage * averageCoral;
                float averageLevelThree = averageLevelThreePercentage * averageCoral;
                float averageLevelFour = averageLevelFourPercentage * averageCoral;

                if (spinner.getSelectedItem().equals("Team Number")) {
                    barValues.add(new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                    xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                } else if (spinner.getSelectedItem().equals("Descending")) {
                    if (barValues.size() == 0) {
                        barValues.add(new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                        xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                    } else {
                        for (int m = 0; m < barValues.size(); m++) {
                            if (averageLevelOne + averageLevelTwo + averageLevelThree + averageLevelFour >= barValues.get(m)[0] + barValues.get(m)[1] + barValues.get(m)[2] + barValues.get(m)[3]) {
                                barValues.add(m, new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                                xAxisLabels.add(m, teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            } else if (m == barValues.size() - 1) {
                                barValues.add(new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                                xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            }
                        }
                    }
                } else if (spinner.getSelectedItem().equals("Ascending")) {
                    if (barValues.size() == 0) {
                        barValues.add(new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                        xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                    } else {
                        for (int n = 0; n < barValues.size(); n++) {
                            if (averageLevelOne + averageLevelTwo + averageLevelThree + averageLevelFour <= barValues.get(n)[0] + barValues.get(n)[1] + barValues.get(n)[2] + barValues.get(n)[3]) {
                                barValues.add(n, new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                                xAxisLabels.add(n, teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            } else if (n == barValues.size() - 1) {
                                barValues.add(new float[] {averageLevelOne, averageLevelTwo, averageLevelThree, averageLevelFour});
                                xAxisLabels.add(teams.get(i).toString().substring(0, teams.get(i).toString().length() - 2));
                                break;
                            }
                        }
                    }
                }
                max.add(averageLevelOne + averageLevelTwo + averageLevelThree + averageLevelFour);
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

            BarDataSet dataSet = new BarDataSet(entries, "");
            dataSet.setColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
            dataSet.setStackLabels(new String[] {"Average Level One", "Average Level Two", "Average Level Three", "Average Level Four"});
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

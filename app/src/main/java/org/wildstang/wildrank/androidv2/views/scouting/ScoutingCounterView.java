package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.wildstang.wildrank.androidv2.R;

import java.util.Map;

public class ScoutingCounterView extends ScoutingView {

    private TextView labelView;
    private TextView countView;

    private Button plus;

    private Button minus;
    private int count;

    public ScoutingCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_counter, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScoutingView, 0, 0);
        String label = a.getString(R.styleable.ScoutingView_label);
        a.recycle();

        labelView = (TextView) findViewById(R.id.label);
        labelView.setText(label);

        countView = (TextView) findViewById(R.id.count);
        countView.setText(Integer.toString(count));

        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);


        boolean hasLabel = label != null && !label.isEmpty(); // Determine whether there's a label

        if (hasLabel) {
            labelView.setText(label);
            labelView.setVisibility(View.VISIBLE); // Show the label
        } else {
            labelView.setVisibility(View.GONE); // Hide the label
        }

        // Make view clickable
        plus.setOnClickListener(v -> {
            count++;
            countView.setText(Integer.toString(count));
        });


        minus.setOnClickListener(v -> {
           if (count>0) {
               count--;
               countView.setText(Integer.toString(count));
           } else {

               countView.setText(Integer.toString(count));
           }
           });
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        countView.setText(Integer.toString(this.count));
    }

    @Override
    public void writeContentsToMap(Map<String, Object> map) {
        map.put(key, count);
    }

    @Override
    public void restoreFromMap(Map<String, Object> map) {
        Object count = map.get(key);
        if (count instanceof Integer) {
            setCount((Integer) count);
        }

    }
}

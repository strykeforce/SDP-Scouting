package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;

import org.wildstang.wildrank.androidv2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ScoutingButtonView extends ScoutingView {
    private Button buttonView;

    private OnClickListener listener;

    private ArrayList<Long> clicks;

    public ScoutingButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_button, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScoutingView, 0, 0);
        String label = a.getString(R.styleable.ScoutingView_label);
        a.recycle();

        buttonView = (Button) findViewById(R.id.button);
        buttonView.setClickable(true);
        // This conflicts with our custom state saving
        buttonView.setSaveEnabled(false);

        clicks = new ArrayList<>();

        this.setOnClickListener(v -> {
            System.out.println("\nButton has been pressed\n");
            clicks.add(Calendar.getInstance().getTimeInMillis());
        });
    }

    @Override
    public void writeContentsToMap(Map<String, Object> map) {
        map.put(key, clicks);
    }

    public void setClicks(ArrayList<Long> list) {
        clicks = list;
    }

    @Override
    public void restoreFromMap(Map<String, Object> map) {
        Object list = map.get(key);
        if (list instanceof ArrayList) {
            setClicks((ArrayList<Long>) list);
        }
    }
}

package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.wildstang.wildrank.androidv2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ScoutingButtonView extends ScoutingView {
    private Button buttonView;
    private TextView AutonWords;
    private ArrayList<Long> clicks;

    public ScoutingButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_button, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScoutingView, 0, 0);
        String label = a.getString(R.styleable.ScoutingView_label);
        a.recycle();

        buttonView = (Button) findViewById(R.id.button);

        AutonWords = (TextView) findViewById(R.id.AutonPreview);
        buttonView.setClickable(true);
        // This conflicts with our custom state saving
        buttonView.setSaveEnabled(false);


        clicks = new ArrayList<>();

        buttonView.setOnClickListener(v ->

        {
            clicks.add(Calendar.getInstance().getTimeInMillis());

            Toast toast = Toast.makeText(getContext(), key, Toast.LENGTH_SHORT);

            toast.show();
            //Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();

            try {
                AutonWords.setText(key);

            } catch (NullPointerException e) {
                Log.e("ScoutingButtonView", "Error finding TextView: " + e.getMessage());
            }
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

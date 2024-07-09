package org.wildstang.wildrank.androidv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;

import java.util.List;
import java.util.Map;

public class PicklistAdapter extends ArrayAdapter<QueryRow> {

    public PicklistAdapter(Context context, List<QueryRow> teams) {
        super(context, R.layout.list_item_team, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PicklistAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater.from(getContext()));
            convertView = inflater.inflate(R.layout.list_item_team, null, false);
            holder = new PicklistAdapter.ViewHolder();
            holder.teamNumber = (TextView) convertView.findViewById(R.id.team_number);
            holder.teamName = (TextView) convertView.findViewById(R.id.team_name);
            convertView.setTag(holder);
        } else {
            holder = (PicklistAdapter.ViewHolder) convertView.getTag();
        }

        QueryRow row = getItem(position);

        Map<String, Object> properties = row.getDocument().getProperties();

        holder.teamNumber.setText(properties.get("team_number").toString());

        holder.teamName.setText(properties.get("nickname").toString());

        return convertView;
    }

    public static class ViewHolder {
        TextView teamNumber;
        TextView teamName;

        public String getNumber() {
            return (String) teamNumber.getText();
        }
    }
}
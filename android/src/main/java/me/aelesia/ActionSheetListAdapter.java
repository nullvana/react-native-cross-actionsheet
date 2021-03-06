package me.aelesia;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.aelesia.R;

import java.util.List;

public class ActionSheetListAdapter extends ArrayAdapter<String> {
    OnItemClickListener listener;
    int destructive;
    String tintColor;
    String backgroundColor;
    String textColor;
    String borderColor;

    public ActionSheetListAdapter(Context context, List<String> users, int destructive, String tintColor,
            String backgroundColor, String textColor, String borderColor, OnItemClickListener listener) {
        super(context, 0, users);
        this.tintColor = tintColor;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.listener = listener;
        this.destructive = destructive;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if (rowView == null) {
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.actionsheet_list_item, parent, false);
            rowView.setBackgroundColor(Color.parseColor(backgroundColor));
        }
        TextView text = (TextView) rowView.findViewById(R.id.actionsheet_item_text);
        text.setText(getItem(position));
        text.setBackgroundColor(Color.parseColor(backgroundColor));
        text.setTextColor(Color.parseColor(textColor));
        if (destructive == position) {
            text.setTextColor(Color.parseColor("#ff3b3a"));
        } else {
            try {
                text.setTextColor(Color.parseColor(tintColor));
            } catch (Exception e) {
                text.setTextColor(Color.parseColor(textColor));
            }
        }

        LinearLayout ll = (LinearLayout) rowView.findViewById(R.id.actionsheet_item);
        ll.setOnClickListener(v -> {
            listener.onItemClick(position);
        });

        return rowView;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

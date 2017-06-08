package com.example.olivier.tobeortohave.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.olivier.tobeortohave.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier on 16/05/2017.
 */

public class AdapterSpinnerOneElement extends ArrayAdapter<SpinnerItem> {
    private Context mContext;
    private ArrayList<SpinnerItem> listState;
    private AdapterSpinnerOneElement myAdapter;
    private boolean isFromView = false;

    public AdapterSpinnerOneElement(Context context, int resource, List<SpinnerItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerItem>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final AdapterSpinnerOneElement.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new AdapterSpinnerOneElement.ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (AdapterSpinnerOneElement.ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).toString());



        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        holder.mCheckBox.setTag(position);

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                SpinnerItem spinnerItem = (SpinnerItem) cb.getTag();

                spinnerItem.setSelected(cb.isChecked());

            }
        });

        SpinnerItem listItem = (SpinnerItem) getItem(position);

        holder.mCheckBox.setChecked(listItem.isSelected());
        holder.mCheckBox.setTag(listItem);


        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}


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
 * Created by Olivier on 14/05/2017.
 */

public class AdapterSpinnertTwoElements extends ArrayAdapter<SpinnerItemTwoElements> {
    private Context mContext;
    private ArrayList<SpinnerItemTwoElements> listState;
    private AdapterSpinnertTwoElements myAdapter;
    private boolean isFromView = false;

    public AdapterSpinnertTwoElements(Context context, int resource, List<SpinnerItemTwoElements> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerItemTwoElements>) objects;
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

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).toString());


        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        holder.mCheckBox.setTag(position);

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {       //Detect on which sensor we have tick or not the checkbox
                CheckBox cb = (CheckBox) v;
                SpinnerItemTwoElements spinnerItemTwoElements = (SpinnerItemTwoElements) cb.getTag();

                spinnerItemTwoElements.setSelected(cb.isChecked());


            }
        });

        SpinnerItemTwoElements listItem = (SpinnerItemTwoElements) getItem(position);

        holder.mCheckBox.setChecked(listItem.isSelected());
        holder.mCheckBox.setTag(listItem);


        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}

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

public class AdapterSpinner extends ArrayAdapter<SpinnerItemDpt> {
    private Context mContext;
    private ArrayList<SpinnerItemDpt> listState;
    private AdapterSpinner myAdapter;
    private boolean isFromView = false;

    public AdapterSpinner(Context context, int resource, List<SpinnerItemDpt> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerItemDpt>) objects;
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

        holder.mTextView.setText(listState.get(position).getDpt());

        // To check weather checked event fire from getview() or user input
        /*isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;*/

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        holder.mCheckBox.setTag(position);

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {       //Detect on which sensor we have tick or not the checkbox
                CheckBox cb = (CheckBox) v;
                SpinnerItemDpt spinnerItemDpt = (SpinnerItemDpt) cb.getTag();

                spinnerItemDpt.setSelected(cb.isChecked());

                /*for (int i = 0; i < Sensors.size(); i++) {

                    if (Objects.equals(csensor.getName(), Sensors.get(i).getName()))
                    {

                        Sensors.get(i).setEnabled(cb.isChecked());      //Storage of the state of the sensor in our ArrayList of sensor

                    }
                }*/

            }
        });

        /*holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                System.out.println("CHANGE : " + listState.get(getPosition).isSelected() + " POSITIOON : " + getPosition);
                listState.get(getPosition).setSelected(listState.get(getPosition).isSelected());


            }
        });*/

        SpinnerItemDpt listItem = (SpinnerItemDpt) getItem(position);

        holder.mCheckBox.setChecked(listItem.isSelected());
        holder.mCheckBox.setTag(listItem);


        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
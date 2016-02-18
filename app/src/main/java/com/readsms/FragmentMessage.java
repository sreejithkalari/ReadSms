package com.readsms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.sreejith kp
 */
public class FragmentMessage extends Fragment {

    View view;
    TextView tvLastMessage;
    TextView tvName;
    TextView tvTemperature;
    TextView tvPressure;
    TextView tvOxygen;

    TextView tvTestMessage;

    String name = null;
    String temperature = null;
    String pressure = null;
    String oxygen = null;
    Button btnChangeNumber;
    StoreInSp sp;


    public FragmentMessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);
        tvLastMessage = (TextView) view.findViewById(R.id.tv_last_value);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        tvPressure = (TextView) view.findViewById(R.id.tv_pressure);
        tvOxygen = (TextView) view.findViewById(R.id.tv_oxygen);
        tvTestMessage = (TextView) view.findViewById(R.id.tv_test_message);
        btnChangeNumber = (Button) view.findViewById(R.id.btn_change_number);


        sp = new StoreInSp(getActivity());
        String message = sp.getStoredData("message");

        name = sp.getStoredData("name");
        temperature = sp.getStoredData("temperature");
        pressure = sp.getStoredData("pressure");
        oxygen = sp.getStoredData("oxygen");

///
        if (message != null) {
            tvTestMessage.setText(message);
        } else {
            tvTestMessage.setText("No Value");
        }
///

        if (name != null) {
            tvName.setText(name);
        } else {
            tvName.setText("No Name");
        }

        if (temperature != null) {
            tvTemperature.setText("Temperature : " + temperature + " \u2109");
        } else {
            tvTemperature.setText("No Value");
        }

        if (pressure != null) {
            tvPressure.setText("Pressure : " + pressure + " pH");
        } else {
            tvPressure.setText("No Value");
        }

        if (oxygen != null) {
            tvOxygen.setText("Dissolved Oxygen : " + oxygen + " ");
        } else {
            tvOxygen.setText("No Value");
        }


        btnChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).replaceFragment(new FragmentSaveNumber());

            }
        });

        return view;
    }

}

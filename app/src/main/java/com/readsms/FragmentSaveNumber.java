package com.readsms;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSaveNumber extends Fragment {

    Button btnSave;
    EditText etNumber;
    String number = "";
    LinearLayout layout;
    TextView tvCurrentNumberSet;
    View view;
    StoreInSp sp;
    String currentNumberSet=null;


    public FragmentSaveNumber() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_save, container, false);

        btnSave = (Button) view.findViewById(R.id.btn_save);
        etNumber = (EditText) view.findViewById(R.id.input_number);
        layout = (LinearLayout) view.findViewById(R.id.layout_main);
        tvCurrentNumberSet = (TextView) view.findViewById(R.id.tv_current_set_number);

        sp = new StoreInSp(getActivity());
        currentNumberSet=sp.getStoredData("number");

        if(currentNumberSet!=null){
            tvCurrentNumberSet.setText("Current Number Set : "+currentNumberSet);
        }
        else{
            tvCurrentNumberSet.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = etNumber.getText().toString();

                if (checkNumber(number)) {

                    sp.storeInSp("number", number);
                    sp.storeInSp("first", "done");

                    showDialog("Number Saved", number + " is Saved Successfully");
                }


            }
        });

        return view;
    }

    private void showDialog(String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                //finish();
                ((MainActivity) getActivity()).replaceFragment(new FragmentMessage());
            }

        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }


    private boolean checkNumber(String number) {

        if (isEmpty(number)) {
            setErrorMsg("Please Enter the Number");
            return false;
        }

        if (!isValidMobileNumber(number)) {
            setErrorMsg("Mobile Number Not Valid");
            return false;
        }

        return true;

    }

    public void setErrorMsg(String errorMsg) {
        etNumber.setError(errorMsg, getResources().getDrawable(R.drawable.ic_edit));
    }


    public static boolean isEmpty(Object object) {

        if (object != null) {

            if (object instanceof String) {
                String input = (String) object;
                if (input.trim().length() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidMobileNumber(String phone_no) {
        if (!Pattern.matches("[a-zA-Z]+", phone_no)) {

            if (phone_no.length() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}

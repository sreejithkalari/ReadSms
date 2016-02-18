package com.readsms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ShowMessageActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvTemperature;
    TextView tvPressure;
    TextView tvOxygen;

    TextView tvTestMessage;

    String name = null;
    String temperature = null;
    String pressure = null;
    String oxygen = null;

    StoreInSp sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        tvPressure = (TextView) findViewById(R.id.tv_pressure);
        tvOxygen = (TextView) findViewById(R.id.tv_oxygen);

        tvTestMessage = (TextView) findViewById(R.id.tv_test_message);

        sp = new StoreInSp(ShowMessageActivity.this);
        String message = sp.getStoredData("message");
        String incomingNumber = sp.getStoredData("incomingNumber");

///
        if (message != null) {
            tvTestMessage.setText(message);
        } else {
            tvTestMessage.setText("No Value");
        }
///


        if (message != null) {
            splitMessage(message);
        }



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

        saveValues();
        deleteSMS(ShowMessageActivity.this,message,incomingNumber);

    }

    private void saveValues() {

        sp.storeInSp("name", name);
        sp.storeInSp("temperature", temperature);
        sp.storeInSp("pressure", pressure);
        sp.storeInSp("oxygen", oxygen);

    }

    private void splitMessage(String message) {

        /*StringTokenizer tokens = new StringTokenizer(message, "@");//33#129@107*095
                String first = tokens.nextToken();// this will contain 33#129
                String second = tokens.nextToken();// this will contain 107*095
                //String test2=	second.replaceAll("E", " ");// this will contain

                StringTokenizer tokens1 = new StringTokenizer(first, "#");//33#129

                String first1 = tokens1.nextToken();// this will contain 33
                //Integer.parseInt(first1.toString());

                String second1 = tokens1.nextToken();// this will contain 129\

                StringTokenizer tokens2 = new StringTokenizer(second, "*");//107*095
                String first2 = tokens2.nextToken();// this will contain 107
                String second2 = tokens2.nextToken();// this will contain 095


                int temp = 0;
                int sys = 0;
                int dys = 0;
                int hrt = 0;

                try {
                    temp = Integer.parseInt(first1.toString());
                    sys = Integer.parseInt(second1.toString());
                    dys = Integer.parseInt(first2.toString());
                    hrt = Integer.parseInt(second2.toString());

                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }*/

    }

    public void deleteSMS(Context context, String message, String number) {

        try {
            logInfo("Deleting SMS from inbox");
            Uri uriSms = Uri.parse("content://sms/inbox");
            Cursor c = context.getContentResolver().query(uriSms,
                    new String[] { "_id", "thread_id", "address",
                            "person", "date", "body" }, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);

                    if (message.equals(body) && address.equals(number)) {
                        logInfo("Deleting SMS with id: " + threadId);
                        context.getContentResolver().delete(
                                Uri.parse("content://sms/" + id), null, null);
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
           logError("Could not delete SMS from inbox: " + e.getMessage());
        }
    }

    private void logInfo(String s) {

        Log.i("SMS","Delete sms: "+s);
    }

    private void logError(String s) {

        Log.e("SMS","Delete sms: "+s);
    }


}

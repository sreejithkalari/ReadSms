package com.readsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private String incomingNumber = null;
   // public static String message = "40#120@80*75";
    public static String message = null;
    String storedNumber=null;
    StoreInSp sp;
    String number;

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        sp = new StoreInSp(context);
        storedNumber=sp.getStoredData("number");

        // this method will be called when phone receives sms
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;

        if (null != bundle) {
            // sms got!!
            Object[] pdus = (Object[]) bundle.get("pdus");
            // msg array is created to hold message
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                // insert sms to msg array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

            }
            // got incoming no from our array
            incomingNumber = msgs[0].getOriginatingAddress();

            number = incomingNumber.replace("+91", "");
            message = msgs[0].getMessageBody();

            System.out.println("@@@@@@@@@@incoming number full: " + incomingNumber);
            System.out.println("@@@@@@@@@@incoming number: " + number);
            System.out.println("@@@@@@@@@@stored Number: " + storedNumber);
            System.out.println("@@@@@@@@@@message: " + message);

            if(storedNumber.equals(number)){
                sp.storeInSp("message",message);
                sp.storeInSp("incomingNumber",incomingNumber);

                Intent intent1= new Intent(context,ShowMessageActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            }



        }
    }


}

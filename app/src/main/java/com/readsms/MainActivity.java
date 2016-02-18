package com.readsms;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // MainActivity

    private static final int MY_PERMISSIONS_REQUEST_SMS = 1;
    private static final String TAG = "READ_SMS";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSMSPermission()) {
            checkFirstTimeOpen();
        } else {
            requestSMSPermission();
        }

    }

    private void checkFirstTimeOpen() {

        StoreInSp sp = new StoreInSp(MainActivity.this);
        String first = sp.getStoredData("first");
        Log.d(TAG, "" + first);

        if (first == null) {
            Log.d(TAG, "save here.. starting new activity");
            // startActivity(new Intent(MainActivity.this,ShowMessageActivity.class));
            replaceFragment(new FragmentSaveNumber());
        } else {
            //getMessage();
            replaceFragment(new FragmentMessage());
        }

    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private boolean checkSMSPermission() {
        int result = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestSMSPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_SMS);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        checkFirstTimeOpen();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkFirstTimeOpen();
                } else {

                    showDialog("Permission Denied", "You Can't Access Sms Function..");
                }
                break;

        }

    }

    private void showDialog(String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_edit));

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();

            }

        });

       /* alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();


            }

        });*/

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }


    @Override
    public void onBackPressed() {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
            if (f instanceof FragmentMessage)
                super.onBackPressed();
            else {
                replaceFragment(new FragmentMessage());
            }
        }

}

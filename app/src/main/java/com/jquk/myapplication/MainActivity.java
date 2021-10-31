package com.jquk.myapplication;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.jquk.myapplication.TelephonyManagerWrapper;

public class MainActivity extends TelephonyManagerWrapper {
    //public class MainActivity extends AppCompatActivity {
    TextView textView1;
    TelephonyManagerWrapper tmw;
    private GsmCellLocation cellLocation;
    int cellid;
    int celllac;
    static boolean TOAST = false;
    static boolean SHOW_PERMISSIONS_LIST = true;
//    static boolean READ_PRIVILEGED_PHONE_STATE_PERMISSION_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.textView1);

        Context context = getApplicationContext();

        //Get the instance of TelephonyManager
        List<String> phone_details = getListOfPermissions(this);
        if (TOAST) {
            Toast toast_permissions_list = Toast.makeText(context, phone_details.get(0), Toast.LENGTH_LONG);
            toast_permissions_list.show();
        }

        textView1.setText(phone_details.get(1));//displaying the information in the textView
        if(SHOW_PERMISSIONS_LIST) {
            textView1.setText(phone_details.get(2));//displaying the information in the textView
        }
    }


}
package com.jquk.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import javax.sql.ConnectionEventListener;

public class TelephonyManagerWrapper extends Activity {
    static boolean READ_PRIVILEGED_PHONE_STATE_PERMISSION_GRANTED = false;
    static CharSequence permission_read_privileged_phone_state_granted = "READ_PRIVILEGED_PHONE_STATE_PERMISSION_GRANTED: true\n";
    static CharSequence permission_read_privileged_phone_state_not_granted = "READ_PRIVILEGED_PHONE_STATE_PERMISSION_GRANTED: false\n";
    static CharSequence permission_read_phone_numbers_granted = "READ_PHONE_NUMBERS: true\n";
    static CharSequence permission_read_phone_numbers_not_granted = "READ_PHONE_NUMBERS: false\n";
    static CharSequence permission_read_sms_granted = "READ_SMS: true\n";
    static CharSequence permission_read_sms_not_granted = "READ_SMS: false\n";
    static CharSequence permission_read_phone_state_granted = "READ_PHONE_STATE: true\n";
    static CharSequence permission_read_phone_state_not_granted = "READ_PHONE_STATE: false\n";
    static CharSequence permission_access_coarse_location_granted = "ACCESS_COARSE_LOCATION: true\n";
    static CharSequence permission_access_coarse_location_not_granted = "ACCESS_COARSE_LOCATION: false\n";
    static CharSequence permission_access_fine_location_granted = "ACCESS_FINE_LOCATION: true\n";
    static CharSequence permission_access_fine_location_not_granted = "ACCESS_FINE_LOCATION: false\n";


//    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    /**
     * getListOfPermissions
     * */
    public List<String> getListOfPermissions(Activity activity) {
        List<String> retVal = new ArrayList<String>();

        String permissions_list = new String("Permissions list:\n");
//        TelephonyManager manager = (TelephonyManager)ActivityCompat.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        String Line1Number;
        String IMEINumber = "";
        String imsi = "";
        String subscriberID = "";
        String SIMSerialNumber = "";
        String networkCountryISO;
        String SIMCountryISO;
        String softwareVersion = "";
        String voiceMailNumber = "";
        String phoneTypeString = "";
        String cellIdentityString = "";
        String cellIsRegistered = "";
        String cellSignalStrengthString = "w";

        networkCountryISO = telephonyManager.getNetworkCountryIso();
        SIMCountryISO = telephonyManager.getSimCountryIso();

        /* READ_PRIVILEGED_PHONE_STATE */
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PRIVILEGED_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        if (!READ_PRIVILEGED_PHONE_STATE_PERMISSION_GRANTED) {
            permissions_list += permission_read_privileged_phone_state_not_granted;
        } else {
            permissions_list += permission_read_privileged_phone_state_granted;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyManager.getImei();// CRASHES
            }
            IMEINumber = telephonyManager.getDeviceId();// CRASHES
            imsi = telephonyManager.getSubscriberId();// CRASHES
            subscriberID = telephonyManager.getDeviceId();// CRASHES
            SIMSerialNumber = telephonyManager.getSimSerialNumber();// CRASHES
        }

        /* READ_PHONE_NUMBERS */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            permissions_list += permission_read_phone_numbers_not_granted;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 0);
            Line1Number = "NULL";
        } else {
            permissions_list += permission_read_phone_numbers_granted;
            Line1Number = telephonyManager.getLine1Number();
        }

        /* READ_SMS */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissions_list += permission_read_sms_not_granted;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
        } else {
            permissions_list += permission_read_sms_granted;
        }

        /* READ_PHONE_STATE */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions_list += permission_read_phone_state_not_granted;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            permissions_list += permission_read_phone_state_granted;
            softwareVersion = telephonyManager.getDeviceSoftwareVersion();
            voiceMailNumber = telephonyManager.getVoiceMailNumber();
        }

        /* ACCESS_FINE_LOCATION */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions_list += permission_access_coarse_location_not_granted;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            permissions_list += permission_access_coarse_location_granted;
        }

        /* ACCESS_FINE_LOCATION */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions_list += permission_access_fine_location_not_granted;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            List<CellInfo> cellInfos = (List<CellInfo>) telephonyManager.getAllCellInfo();
            permissions_list += permission_access_fine_location_granted;
            cellInfos = (List<CellInfo>) telephonyManager.getAllCellInfo();
            for(CellInfo cellInfo : cellInfos)
            {
                CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
                CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
                cellIdentityString = cellIdentity.toString();
                cellIsRegistered = "cellIsRegistered_" + cellInfoGsm.isRegistered();
                cellSignalStrengthString = cellSignalStrengthGsm.toString();
            }
        }

        // toast or not to toast
//        if (TOAST) {
//            Toast toast_permissions_list = Toast.makeText(this, permissions_list, Toast.LENGTH_LONG);
//            toast_permissions_list.show();
//        }

        // Get the phone type
        int phoneType = telephonyManager.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneTypeString = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneTypeString = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeString = "NONE";
                break;
        }

        boolean isRoaming = telephonyManager.isNetworkRoaming();

        // prepare the string to be shown in the list
        String phone_details = "Phone Details:\n";
        phone_details += "\n Line1 Number: " + Line1Number;
        phone_details += "\n IMEI: " + IMEINumber;
        phone_details += "\n IMSI: " + imsi;
        phone_details += "\n SubscriberID: " + subscriberID;
        phone_details += "\n Sim Serial Number: " + SIMSerialNumber;
        phone_details += "\n Network Country ISO: " + networkCountryISO;
        phone_details += "\n SIM Country ISO: " + SIMCountryISO;
        phone_details += "\n Software Version: " + softwareVersion;
        phone_details += "\n Voice Mail Number: " + voiceMailNumber;
        phone_details += "\n Phone Network Type: " + phoneTypeString;
        phone_details += "\n Roaming: " + isRoaming;
        phone_details += "\n Cell identity: " + cellIdentityString;
        phone_details += "\n Cell is registered: " + cellIsRegistered;
        phone_details += "\n Cell signal strength: " + cellSignalStrengthString;

        retVal.add(permissions_list);
        retVal.add(phone_details);
        return retVal;
    }

//    /**
//     * Get Cell info (telephony base station tower)
//     * */
//    public List<String> getCellInfo(Activity activity) {
//        List<String> retVal = new ArrayList<String>();
//
//        List<CellInfo> cellInfos;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//        } else {
//            cellInfos = (List<CellInfo>) telephonyManager.getAllCellInfo();
//            for(CellInfo cellInfo : cellInfos)
//            {
//                CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
//                CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
//                CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
////                Log.d("cell", "registered: " + cellInfoGsm.isRegistered());
////                Log.d("cell", cellIdentity.toString());
////                Log.d("cell", cellSignalStrengthGsm.toString());
//                retVal.add("cellIsRegistered_" + cellInfoGsm.isRegistered());
//                retVal.add(cellIdentity.toString());
//                retVal.add(cellSignalStrengthGsm.toString());
//            }
//        }
//        return retVal;
//    }
}

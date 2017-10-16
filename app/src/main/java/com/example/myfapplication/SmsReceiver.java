package com.example.myfapplication;
import android.support.v7.widget.Toolbar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by חן קאשי on 05/09/2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    private String TAG = SmsReceiver.class.getSimpleName();

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent // Retrieves a map of extended data from the intent.
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

                try {

                    if (bundle != null) {

                        final Object[] pdusObj = (Object[]) bundle.get("pdus");

                        for (int i = 0; i < pdusObj.length; i++) {

                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                            String senderNum = phoneNumber;
                            String message = currentMessage.getDisplayMessageBody();

                            Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                            // Show Alert
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context,
                                    "senderNum: "+ senderNum + ", message: " + message, duration);
                            toast.show();

                        } // end for loop
                    } // bundle is null

                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" +e);

                }
    }
}
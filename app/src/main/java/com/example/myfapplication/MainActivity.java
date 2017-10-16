package com.example.myfapplication;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.telephony.gsm.SmsManager;
import android.widget.Button;

import com.example.myfapplication.bluetoothscanner.ListActivity;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
public static android.content.Context context;
    public static int REQUEST_BLUETOOTH = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button btnSendSMS;
    String phoneNo;
    String message;

    int cnt =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();

        btnSendSMS = (Button) findViewById(R.id.btnSSMS);
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.lbl1);
                //for the first permission
                if(cnt==1){
                    tv.setText("will send now SMS that num:"+Integer.toString(cnt));
                    sendSMSMessage();
                    cnt++;
                }//for the next send after permission was expected
                else{
                    sendSMSAgain();
                    //Log.e(String.valueOf(tag), "hereIAm0");
                }
           }
       });

  }

    public void Btn3Click(View v) {

        try {
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            Button btn = (Button)findViewById(R.id.GoBluetooth);
            TextView tv = (TextView) findViewById(R.id.lbl1);
            tv.setText("GO to Scan");
            startActivity(new Intent(MainActivity.this, ListActivity.class));

        } catch (Exception ex) {
            TextView tv1 = (TextView) findViewById(R.id.lbl1);
            tv1.setText(ex.getMessage());
        }
    }


    //to call automate to phone: enter phone only.
    public void Btn1Click(View v) {

        String phoneEntered="0509791974";
        TextView tv = (TextView) findViewById(R.id.lbl1);
        tv.setText("dialing");
        try {
          //  TextView tv0 = (TextView) findViewById(R.id.lbl1);

            String number = ("tel:"+ phoneEntered);
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            mIntent.setData(Uri.parse(number));
            // Here, thisActivity is the current activity
            if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                   new String[]{android.Manifest.permission.CALL_PHONE},
                    123);

                //now will be the ask to the user about the permissiom
                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                   startActivity(mIntent);
                    TextView tv2 = (TextView) findViewById(R.id.lbl1);
                    tv2.setText("end call");


                } catch(SecurityException e) {
                    e.printStackTrace();
                }
            }
            // startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0532864277")));
        } catch (Exception ex) {
            TextView tv1 = (TextView) findViewById(R.id.lbl1);
            tv1.setText(ex.getMessage());
        }

    }

    //  to send sms to phone: enter only phone and content msg.
    protected void sendSMSMessage() {
                phoneNo = "0542664055";
                message = "hello it is SMS from henApp";

                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    protected void sendSMSAgain() {
       // phoneNo ="0523518885";
        phoneNo ="0542664055";
        message = "hello it is SMS from henApp";
        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            makeText(getApplicationContext(), "SMS sent Again.",
                    LENGTH_LONG).show();
        } catch (Exception ex){
            makeText(getApplicationContext(),
                    "SMS faild, please try again.", LENGTH_LONG).show();
            return;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    makeText(getApplicationContext(), "SMS sent.",
                            LENGTH_LONG).show();
                } else {
                    makeText(getApplicationContext(),
                            "SMS faild, please try again.", LENGTH_LONG).show();
                    return;
                }
            }
        }

    }


}

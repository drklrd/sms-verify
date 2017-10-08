package com.example.drklrd.smsverify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

/**
 * Created by drklrd on 10/8/17.
 */

public class Incoming extends BroadcastReceiver{

    public void onReceive(Context ctx, Intent intent){
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage;
                    if(Build.VERSION.SDK_INT >=19){
                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                         currentMessage = msgs[0];
                    }else{
                         currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
//                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//                    if(phoneNumber == "some excepted phone number"){
//                         You can add logic for expected phone number here !
//                    }
                    String message = currentMessage.getDisplayMessageBody();
                    Intent virtualIntent = new Intent("code");
                    virtualIntent.putExtra("message",message);
                    LocalBroadcastManager.getInstance(ctx).sendBroadcast(virtualIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

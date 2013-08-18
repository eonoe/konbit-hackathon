/**
 * 
 */
package com.example.texttospeach.service;

import com.example.texttospeach.SMSGatewayActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * @author eonoe
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = SMSReceiver.class.getSimpleName();
	private BroadcastReceiver receiver;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "SMS received.");
		
		//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "", sender = "", msg = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str += "SMS from " + msgs[i].getOriginatingAddress();
                sender = msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();
                msg = msgs[i].getMessageBody().toString();
                str += "\n";        
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            SMSGatewayActivity.showSMS(sender, msg);
        }                 		
	}
}
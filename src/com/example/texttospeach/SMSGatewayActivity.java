package com.example.texttospeach;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.texttospeach.service.SMSSenderService;

public class SMSGatewayActivity extends Activity implements OnInitListener {

	private static LinearLayout smsLayout;
	private static Context mContext;
	private static TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts_main_activity_layout);
		smsLayout = (LinearLayout) findViewById(R.id.text_layout);
		mContext = this;
		// Initialize the tts object
		tts = new TextToSpeech(this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ttsmain, menu);
		return true;
	}

	public static void showSMS(String sender, String message) {
		if (message.contains("@")){
			
		}else{
			TextView text = new TextView(mContext);
			text.setText("Sender: " + sender + " Message: " + message);
			smsLayout.addView(text);
			SMSSenderService.getInstance().sendSMS(sender, message);
			speakOut(message);
		}
	}

	private static void speakOut(String message) {
		// Get the text to speak
		if (!TextUtils.isEmpty(message)) {
			tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	@Override
	protected void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		// TTS is successfully initialized
		if (status == TextToSpeech.SUCCESS) {
			// Setting speech language
			int result = tts.setLanguage(Locale.US);
			// If your device doesn't support language you set above
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Cook simple toast message with message
				Toast.makeText(this, "Language not supported",
						Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
			}
			// TTS is not initialized properly
		} else {
			Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
					.show();
			Log.e("TTS", "Initilization Failed");
		}
	}

}

package com.example.texttospeach.async;

import android.os.AsyncTask;

import com.example.texttospeach.service.SMSSenderService;

public class SMSPostServiceAsyncTask extends AsyncTask<Void, Void, Void> {

	private String sender, message;

	public SMSPostServiceAsyncTask(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	@Override
	protected Void doInBackground(Void... params) {
		SMSSenderService.getInstance().sendSMS(sender, message);
		return null;
	}

}

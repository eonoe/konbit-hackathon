package com.example.texttospeach.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;
import android.util.Log;

public class ServiceHelper {

	private final static String TAG = ServiceHelper.class.getSimpleName();

	private final static String URL = "http://localhost:8080";

	public static void sendPost(String sender, String message) {
		if (!TextUtils.isEmpty(sender) && !TextUtils.isEmpty(message))
			postRemote(URL, sender, message);
	}

	private static String postRemote(String url, String sender, String message) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		InputStream inputStream = null;
		try {
			// Set parameters to send
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("sender", sender));
			nameValuePairs.add(new BasicNameValuePair("message", message));

			// Encode and set entity
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			// Execute
			HttpResponse response = client.execute(post);
			Log.d(TAG, "Status CODE --> "
					+ response.getStatusLine().getStatusCode());

		} catch (Exception e) {
			post.abort();
			Log.e(TAG, e.getMessage());
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
		}
		return null;
	}

}

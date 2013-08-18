package com.example.texttospeach.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ServiceHelper {

	private final static String TAG = ServiceHelper.class.getSimpleName();

	private final static String URL = "http://192.168.1.103/konbit/script_insert.php?";

	public static void sendPost(String sender, String message) {
		if (!TextUtils.isEmpty(sender) && !TextUtils.isEmpty(message))
			getRemote(URL, sender, message);
	}

	private static String getRemote(String url, String sender, String message) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("sender", sender));
		params.add(new BasicNameValuePair("msg", message));

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(getHttpParameters(url, params));
		InputStream inputStream = null;
		try {
			HttpResponse getResponse = client.execute(getRequest);
			int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(TAG, "Error Status Code -> " + statusCode);
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			inputStream = getResponseEntity.getContent();//new GZIPInputStream(getResponseEntity.getContent());

			int i = 0;
			List<Byte> bytes = new LinkedList<Byte>();
			while ((i = inputStream.read()) != -1) {
				bytes.add(new Byte((byte) i));
			}
			byte[] data = new byte[bytes.size()];
			int x = 0;
			for (Byte b : bytes) {
				data[x++] = b.byteValue();
			}

			String jsonString = new String(data, "UTF-8");
			return jsonString;
		} catch (Exception e) {
			getRequest.abort();
			Log.e(TAG, "readRemote", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e(TAG, "readRemote", e);
				}
		}
		return null;

	}

	/**
	 * Uses GET Method.
	 * 
	 * @param uri
	 * @param params
	 * @return
	 * @throws BPDPoisException
	 */
	private static String getHttpParameters(String uri, List<BasicNameValuePair> params) {
		try {
			Uri.Builder builder = Uri.parse(uri).buildUpon();
			for (BasicNameValuePair par : params) {
				builder.appendQueryParameter(par.getName(), par.getValue());
			}
			Log.d(TAG, "URL:" + builder.build().toString());
			return builder.build().toString();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

}

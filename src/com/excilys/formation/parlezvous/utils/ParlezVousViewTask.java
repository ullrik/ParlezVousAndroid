package com.excilys.formation.parlezvous.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class ParlezVousViewTask extends AsyncTask<String, String, Boolean> {

	private Intent intent;
	private Context context;
	public ParlezVousViewTask(Intent intent, Context context) {
		super();
		this.intent = intent;
		this.context  =  context;
	}
	public static final String SERVER = "parlezvous.herokuapp.com";
	public static final int PORT = 9000;
	public String monMessage;		
	

	@Override
	protected Boolean doInBackground(String... params) {
		monMessage = params[0];
		
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://" + SERVER + "/message/" + username + "/" + password + "/" + monMessage);

		String content = null;
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			content = InputStreamToString.convert(entity.getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Boolean.valueOf(content);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		String messageNotification;
		if (result) {
			messageNotification = "message erreur";
			//intent.setAction("com.excilys.formation.parlezvous.ParlezVousRedirect.MY_OWN_ACTION");
		} else {
			messageNotification = "message envoyé";
		}
		Toast.makeText(context, messageNotification, Toast.LENGTH_SHORT).show();
		//if (!result)
			//context.message.setText("");

	}


}
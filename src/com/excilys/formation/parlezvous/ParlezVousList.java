package com.excilys.formation.parlezvous;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.excilys.formation.parlezvous.utils.InputStreamToString;
import com.excilys.formation.parlezvous.utils.PrefsHelper;

public class ParlezVousList extends ListActivity {

	private final String TAG = ParlezVousList.class.getSimpleName();

	private PrefsHelper prefshelper;
	private Button btnRefresh;
	private ProgressBar loading;

	public static final String SERVER = "parlezvous.herokuapp.com";

	ArrayList<Message> messages;
	AwesomeAdapter adapter;
	EditText text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");

		//loading.setVisibility(View.VISIBLE);

		setContentView(R.layout.main);
		prefshelper = new PrefsHelper(getApplicationContext());
		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		loading = (ProgressBar) findViewById(R.id.progressBarListMessage);

		new ParlezVousListMessageTask().execute();
		messages = new ArrayList<Message>();
		btnRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new ParlezVousListMessageTask().execute();
			}
		});

	}

	private class ParlezVousListMessageTask extends AsyncTask<String, String, String> {

		public static final String SERVER = "parlezvous.herokuapp.com";
		
		@Override
		protected void onPreExecute() {
			loading.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://" + SERVER + "/messages/"
					+ prefshelper.getName() + "/" + prefshelper.getPassword());

			String content = null;
			try {
				HttpResponse response = client.execute(httpGet);
				content = InputStreamToString.convert(response.getEntity().getContent());
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return content;

		}

		@Override
		protected void onPostExecute(String result) {
			String[] contentSplit = result.split(";");
			for (int i = 0; i < contentSplit.length; i++) {
				String[] strNameMessage = contentSplit[i].split(":");

				if (prefshelper.getName().equals(strNameMessage[0]))
					messages.add(new Message(strNameMessage[1], true));
				else
					messages.add(new Message("<u><b>" + strNameMessage[0]
							+ " :</b></u><br />" + strNameMessage[1], false));

			}

			adapter = new AwesomeAdapter(getApplicationContext(), messages);
			setListAdapter(adapter);
			loading.setVisibility(View.INVISIBLE);

		}

	}

}
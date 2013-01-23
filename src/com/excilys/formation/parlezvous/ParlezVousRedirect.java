package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.excilys.formation.parlezvous.utils.PrefsHelper;

public class ParlezVousRedirect extends Activity {

	final Context context = this;

	private TextView textUserNameConnect;
	private Button envoyerMessage;
	private Button listeMessage;
	PrefsHelper prefshelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_connect);
		prefshelper = new PrefsHelper(ParlezVousRedirect.this);

		envoyerMessage = (Button) findViewById(R.id.buttonEnvoyerMessage);
		listeMessage = (Button) findViewById(R.id.buttonListeMessage);
		textUserNameConnect = (TextView) findViewById(R.id.textUserNameConnect);
		textUserNameConnect.setText(prefshelper.getName());

		envoyerMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// setContentView(R.layout.activity_send);
				Intent pageSend = new Intent(ParlezVousRedirect.this,
						ParlezVousSend.class);
				startActivity(pageSend);

			}
		});

		listeMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// setContentView(R.layout.activity_send);
				Intent pageSend = new Intent(ParlezVousRedirect.this,
						ParlezVousList.class);
				startActivity(pageSend);

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menulogout) {
			Intent pageSend = new Intent(ParlezVousRedirect.this,
					ParlezVousActivity.class);
			prefshelper.removeUser();
			startActivity(pageSend);
		}

		return super.onOptionsItemSelected(item);
	}
}

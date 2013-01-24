package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.excilys.formation.parlezvous.utils.PrefsHelper;

public class ParlezVousRedirect extends Activity {

	private final String TAG = ParlezVousSend.class.getSimpleName();

	final Context context = this;
	private TextView textUserNameConnect;
	private Button envoyerMessage;
	private Button listeMessage;
	PrefsHelper prefshelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");

		// Utilisation du layout activity_redirect
		setContentView(R.layout.activity_redirect);
		// Initialisation pour travaillé avec les composants du layout
		initialize();
		// Methode qui contient les actions click bouton
		actionClick();

	}

	private void actionClick() {
		// Action qui renvoye sur la page Envoyer un message
		envoyerMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent pageSend = new Intent(ParlezVousRedirect.this,
						ParlezVousSend.class);
				startActivity(pageSend);

			}
		});

		// Action qui renvoye sur la page Liste des messages
		listeMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent pageSend = new Intent(ParlezVousRedirect.this,
						ParlezVousList.class);
				startActivity(pageSend);

			}
		});
		
	}

	// Initialisation pour travaillé avec les composants du layout
	private void initialize() {
		prefshelper = new PrefsHelper(ParlezVousRedirect.this);
		envoyerMessage = (Button) findViewById(R.id.buttonEnvoyerMessage);
		listeMessage = (Button) findViewById(R.id.buttonListeMessage);
		textUserNameConnect = (TextView) findViewById(R.id.textUserNameConnect);
		textUserNameConnect.setText(prefshelper.getName());
	}

	/*
	 * Utilisation du bouton Menu d'Android
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	/*
	 * Va charger le menu menulogout pour l'option Déconnexion
	 */
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

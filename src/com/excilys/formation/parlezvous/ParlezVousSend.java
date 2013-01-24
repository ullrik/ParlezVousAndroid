package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.excilys.formation.parlezvous.utils.ParlezVousSendTask;
import com.excilys.formation.parlezvous.utils.PrefsHelper;

public class ParlezVousSend extends Activity {

	private final String TAG = ParlezVousSend.class.getSimpleName();

	private EditText message;
	private Button sendButton;
	private PrefsHelper prefshelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");

		// Utilisation du layout activity_main
		setContentView(R.layout.activity_send);
		// Initialisation pour travaillé avec les composants du layout
		initialize();

		// Methode qui contient les actions click bouton
		actionClick();

	}

	// Initialisation pour travaillé avec les composants du layout
	private void initialize() {
		message = (EditText) findViewById(R.id.editTextMessage);
		sendButton = (Button) findViewById(R.id.buttonSend);
		prefshelper = new PrefsHelper(getApplicationContext());
	}

	// Action qui renvoye sur la page Envoyer un message
	private void actionClick() {
		/*
		 * 1) Verrifie que le champ text n'est pas vide 2) Si ok renvoye sur la
		 * methode ParlezVousTask() pour traiter et envoyer les données Sinon
		 * affiche un message
		 */
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (hasEmptyFields()) {
					Toast.makeText(ParlezVousSend.this,
							"Votre message est vide !", Toast.LENGTH_SHORT)
							.show();
				} else {
					String monMessage = message.getText().toString();
					new ParlezVousSendTask(getApplicationContext())
							.execute(monMessage);
				}
			}
		});
	}

	private boolean hasEmptyFields() {
		return TextUtils.isEmpty(message.getText());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause!");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart!");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState!");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume!");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState!");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy!");
	}

}
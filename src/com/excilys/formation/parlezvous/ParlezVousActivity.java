package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.excilys.formation.parlezvous.utils.ParlezVousActivityTask;
import com.excilys.formation.parlezvous.utils.PrefsHelper;

/**
 * ParlezVousActivity est une classe qui va traiter le module de connexion On
 * recupére : Login MDP
 * 
 * @author Mickael MORISSEAU
 * @author Julian NORMAND
 * 
 */
public class ParlezVousActivity extends Activity {

	private final String TAG = ParlezVousSend.class.getSimpleName();

	private EditText usernameField;
	private EditText passwordField;
	private Button cleanButton;
	private Button sendButton;
	private ProgressBar loading;
	private TextView errorMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");

		// Utilisation du layout activity_main
		setContentView(R.layout.activity_main);
		// Initialisation pour travaillé avec les composants du layout
		initialize();
		/*
		 * PrefsHelper permet de d'enregister des variables qui seront
		 * disponible dans les autres classe via un appel de PrefsHelper
		 */
		PrefsHelper prefshelper = new PrefsHelper(ParlezVousActivity.this);

		/*
		 * Verrification du contenu des variable prefshelper.getName()
		 * prefshelper.getPassword()
		 */
		if ((prefshelper.getName() != "") && (prefshelper.getPassword() != "")
				&& (prefshelper.getName() != "name")
				&& (prefshelper.getPassword() != "password")
				&& (prefshelper.getPassword() != null)
				&& (prefshelper.getName() != null)) {
			Intent intent = new Intent(getApplicationContext(),
					ParlezVousRedirect.class);
			startActivity(intent);
			finish();
		}
		// Methode qui contient les actions click bouton
		actionClick();
	}

	private void actionClick() {
		// Action qui supprime le contenu dans les champs Login et MDP via le
		// bouton Vider
		cleanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				usernameField.setText("");
				passwordField.setText("");
			}
		});

		/*
		 * Action qui recupére le contenu dans les champs Login et MDP via le
		 * bouton Connexion Et renvoie à la classe ParlezVousActivityTask pour
		 * le traitement
		 */
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (hasEmptyFields()) {
					errorMessage.setVisibility(View.VISIBLE);
				} else {
					String username = usernameField.getText().toString();
					String password = passwordField.getText().toString();
					new ParlezVousActivityTask(ParlezVousActivity.this,
							loading, getApplicationContext()).execute(username,
							password);
				}
			}
		});
		
	}

	// Initialisation pour travaillé avec les composants du layout
	private void initialize() {
		usernameField = (EditText) findViewById(R.id.username_field);
		passwordField = (EditText) findViewById(R.id.password_field);
		cleanButton = (Button) findViewById(R.id.clean_button);
		sendButton = (Button) findViewById(R.id.send_button);
		loading = (ProgressBar) findViewById(R.id.loading);
		errorMessage = (TextView) findViewById(R.id.error_message);

	}

	private boolean hasEmptyFields() {
		return TextUtils.isEmpty(usernameField.getText())
				|| TextUtils.isEmpty(passwordField.getText());
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
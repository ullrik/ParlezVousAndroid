package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

		/*
		 * deconnexion.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		 * context); // set title alertDialogBuilder.setTitle("DÈconnecter"); //
		 * Voulez-vous vous déconnecter ? // set dialog message
		 * alertDialogBuilder.setMessage("Voulez-vous vous dÈconnecter ?");
		 * alertDialogBuilder.setCancelable(false);
		 * alertDialogBuilder.setPositiveButton("Yes", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { // if this button is
		 * clicked, close // current activity ParlezVousRedirect.this.finish();
		 * Toast.makeText(ParlezVousRedirect.this, "Deconnecter",
		 * Toast.LENGTH_SHORT) .show();
		 * 
		 * } }); alertDialogBuilder.setNegativeButton("No", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { // if this button is
		 * clicked, just close // the dialog box and do nothing dialog.cancel();
		 * } });
		 * 
		 * // create alert dialog AlertDialog alertDialog =
		 * alertDialogBuilder.create();
		 * 
		 * // show it alertDialog.show(); } });
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Création d'un MenuInflater qui va permettre d'instancier un Menu XML
		// en un objet Menu
		MenuInflater inflater = getMenuInflater();
		// Instanciation du menu XML spécifier en un objet Menu
		inflater.inflate(R.layout.menu, menu);

		return true;
	}

	// Méthode qui se déclenchera au clic sur un item
	public boolean onOptionsItemSelected(MenuItem item) {
		// On regarde quel item a été cliqué grâce à son id et on déclenche une
		// action
		switch (item.getItemId()) {
		case R.id.deconnexion:
			// Pour la deconnexion
			prefshelper.saveUser("", "");
			Intent pageSend = new Intent(ParlezVousRedirect.this,
					ParlezVousActivity.class);
			startActivity(pageSend);
			return true;
		}
		return false;
	}
}

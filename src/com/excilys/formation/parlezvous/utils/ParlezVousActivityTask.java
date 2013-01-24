package com.excilys.formation.parlezvous.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.excilys.formation.parlezvous.ParlezVousRedirect;

public class ParlezVousActivityTask extends AsyncTask<String, String, Boolean> {

	private Activity activity;
	public static final String SERVER = "parlezvous.herokuapp.com";
	public String username;
	public String password;
	private ProgressBar loading;
	private Context context;
	private PrefsHelper prefshelper;

	public ParlezVousActivityTask(Activity _activity, ProgressBar _loading,
			Context _context) {
		super();
		this.activity = _activity;
		this.loading = _loading;
		this.context = _context;
	}

	@Override
	protected void onPreExecute() {
		loading.setVisibility(View.VISIBLE);
	}

	/*
	 * Connexion au site avec methode GET pour la connexion
	 * URL : http://[SERVER]/connect/[Login]/[MDP] 
	 * Variable :
	 * 		params[0] => username
	 * 		params[2] => password
	 */
	@Override
	protected Boolean doInBackground(String... params) {
		username = params[0];
		password = params[1];
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://" + SERVER + "/connect/"
				+ username + "/" + password);

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

	/*
	 * Si on recupére un contenu (True) les informations utilisateur sont correcter
	 * Sinon il y a une erreur dans les identifiants
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		String message;
		if (result) {
			message = "Utilisateur connecté";
			// Stocke les variable username et password dans prefshelper
			Intent intent = new Intent(context, ParlezVousRedirect.class);
			prefshelper = new PrefsHelper(context);
			prefshelper.saveUser(username, password);
			activity.startActivity(intent);
		} else {
			message = "Identifiant incorrecte !";
		}
		loading.setVisibility(View.INVISIBLE);
		// Affiche un message à l'utilisateur pour le renseigné
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}

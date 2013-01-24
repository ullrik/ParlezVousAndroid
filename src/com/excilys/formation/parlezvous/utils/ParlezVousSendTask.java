package com.excilys.formation.parlezvous.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

public class ParlezVousSendTask extends AsyncTask<String, String, Boolean> {

	public static final String SERVER = "parlezvous.herokuapp.com";
	private String monMessage;
	private PrefsHelper prefshelper;
	private Context context;

	public ParlezVousSendTask(Context _context) {
		this.context = _context;
	}

	/*
	 * Connexion au site avec methode GET pour la connexion URL :
	 * http://[SERVER]/message/[Login]/[MDP]/[Message] Variable : params[0] =>
	 * monMessage
	 */
	@Override
	protected Boolean doInBackground(String... params) {
		monMessage = params[0];
		prefshelper = new PrefsHelper(context);
		DefaultHttpClient client = new DefaultHttpClient();
		/*
		 * Transformation du message text pour etre utilisé dans une URL
		 */
		String query = "http://" + SERVER + "/message/" + prefshelper.getName()
				+ "/" + prefshelper.getPassword() + "/"
				+ Uri.encode(monMessage.replaceAll("\\n", "<br>"));
		HttpGet httpGet = new HttpGet(query);

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
	 * Si on recupére un contenu c'est qu'il y a eu une erreur donc le message
	 * n'a pas été envoyé Sinon le message a bien été envoyé
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		String messageNotification;
		if (result) {
			messageNotification = "message erreur";
		} else {
			messageNotification = "message envoyé";
		}
		// Avertir l'utilisateur pour savoir si le message à bien été envoyé ou
		// non
		Toast.makeText(context, messageNotification, Toast.LENGTH_LONG).show();
		// Si le message a bien été envoyé on peux vider le champ text
		// if (!result)
		// message.setText("");

	}
}

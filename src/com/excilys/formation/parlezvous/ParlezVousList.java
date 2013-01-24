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

/**
 * ParlezVousList est une classe qui va traiter les données contenu sur le
 * serveur et les afficher en liste
 * 
 * @author Mickael MORISSEAU
 * @author Julian NORMAND
 * 
 */
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
		
		// Utilisation du layout activity_list
		setContentView(R.layout.activity_list);
		// Initialisation pour travaillé avec les composants du layout
		initialize();

		// Renvoie à la classe ParlezVousListMessageTask pour le traitement
		new ParlezVousListMessageTask().execute();
		messages = new ArrayList<Message>();
		/*
		 * Quand on clique sur le bouton rafraichir Renvoie à la classe
		 * ParlezVousListMessageTask pour le traitement
		 */
		btnRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new ParlezVousListMessageTask().execute();
			}
		});

	}

	// Initialisation pour travaillé avec les composants du layout
	private void initialize() {
		prefshelper = new PrefsHelper(getApplicationContext());
		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		loading = (ProgressBar) findViewById(R.id.progressBarListMessage);

	}

	private class ParlezVousListMessageTask extends
			AsyncTask<String, String, String> {

		public static final String SERVER = "parlezvous.herokuapp.com";

		@Override
		protected void onPreExecute() {
			loading.setVisibility(View.VISIBLE);
		}

		/*
		 * Connexion au site avec methode GET et récupere le contenu de la page
		 * URL : http://[SERVER]/messages/[Login]/[MDP] content : contient le
		 * contenu de la page
		 */
		@Override
		protected String doInBackground(String... params) {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://" + SERVER + "/messages/"
					+ prefshelper.getName() + "/" + prefshelper.getPassword());

			String content = null;
			try {
				HttpResponse response = client.execute(httpGet);
				content = InputStreamToString.convert(response.getEntity()
						.getContent());

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return content;

		}

		/*
		 * Traite le contenu de la page content :
		 * [auteur]:[message1];[auteur]:[message2];...
		 */
		@Override
		protected void onPostExecute(String result) {
			// Découpe la chaine pour retenir que [auteur]:[message]; dans un
			// tableau
			String[] contentSplit = result.split(";");
			for (int i = 0; i < contentSplit.length; i++) {
				/*
				 * Découpe la chaine pour avoir strNameMessage[0] : auteur
				 * strNameMessage[1] : message
				 */
				String[] strNameMessage = contentSplit[i].split(":");

				// Verrification pour savoir s'il s'agit de moi ou d'un autre
				// utilisateur
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
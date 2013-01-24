package com.excilys.formation.parlezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.excilys.formation.parlezvous.utils.ParlezVousActivityTask;
import com.excilys.formation.parlezvous.utils.PrefsHelper;

public class ParlezVousActivity extends Activity {

	private EditText usernameField;
	private EditText passwordField;
	private Button cleanButton;
	private Button sendButton;
	private ProgressBar loading;
	private TextView errorMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		PrefsHelper prefshelper = new PrefsHelper(ParlezVousActivity.this);

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

		cleanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				usernameField.setText("");
				passwordField.setText("");
			}
		});

		/*
		 * usernameField.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { usernameField.setFocusable(true); } });
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

	/*
	 * public void setItemsCanFocus(boolean itemsCanFocus) { usernameField =
	 * itemsCanFocus; if (!itemsCanFocus) {
	 * setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); } }
	 */

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
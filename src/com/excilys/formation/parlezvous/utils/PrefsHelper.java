package com.excilys.formation.parlezvous.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefsHelper {

	private static final String PASSWORD = "password";
	private static final String NAME = "name";
	private SharedPreferences prefshelper;

	public PrefsHelper(Context context) {
		prefshelper = context.getSharedPreferences("prefshelper",
				Context.MODE_PRIVATE);
	}

	public void saveUser(String name, String password) {
		Editor edit = prefshelper.edit();
		edit.putString(NAME, name);
		edit.putString(PASSWORD, password);
		edit.apply();
	}

	public void removeUser() {
		Editor edit = prefshelper.edit();
		edit.putString(NAME, "");
		edit.putString(PASSWORD, "");
		edit.apply();
	}

	public String getPassword() {
		return prefshelper.getString(PASSWORD, null);
	}

	public String getName() {
		return prefshelper.getString(NAME, null);
	}
}

package com.excilys.formation.parlezvous;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * AwesomeAdapter est une classe personnalis�e pour la mise en �uvre de ligne
 * personnalis�e dans ListView
 * 
 * @author Adil Soomro
 * 
 */
public class AwesomeAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Message> mMessages;

	public AwesomeAdapter(Context context, ArrayList<Message> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}

	public int getCount() {
		return mMessages.size();
	}

	public Object getItem(int position) {
		return mMessages.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = (Message) this.getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView
					.findViewById(R.id.message_text);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// Converti notre chaine de caractere String au fromat HTML.
		holder.message.setText(Html.fromHtml(message.getMessage()));

		LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		/*
		 * V�rifie l'�tat du message puis retire le fond, et change la couleur
		 * du texte.
		 */
		if (message.isStatusMessage()) {
			holder.message.setBackgroundDrawable(null);
			lp.gravity = Gravity.LEFT;
		} else {
			/*
			 * Si je suis l'exp�diteur du message alors la couleur est verte et
			 * affich� � droite
			 */
			if (message.isMine()) {
				holder.message
						.setBackgroundResource(R.drawable.speech_bubble_green);
				lp.gravity = Gravity.RIGHT;
			}
			/*
			 * Si je ne suis pas l'exp�diteur du message alors la couleur est
			 * orange et affich� � gauche
			 */
			else {
				holder.message
						.setBackgroundResource(R.drawable.speech_bubble_orange);
				lp.gravity = Gravity.LEFT;
			}
			holder.message.setLayoutParams(lp);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView message;
	}

	public long getItemId(int position) {
		// Non impl�ment�e, parce que nous n'utilisons pas SQLite.
		return 0;
	}

}

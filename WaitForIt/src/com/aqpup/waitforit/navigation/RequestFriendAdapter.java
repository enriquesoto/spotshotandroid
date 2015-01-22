package com.aqpup.waitforit.navigation;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqpup.waitforit.R;

public class RequestFriendAdapter extends BaseAdapter {

	private Activity activity;
	private List<Item_request_friend> arrayitms;

	public RequestFriendAdapter(Activity activity, List<Item_request_friend> listarry) {
		super();
		this.activity = activity;
		this.arrayitms = listarry;
	}

	@Override
	public Object getItem(int position) {
		return arrayitms.get(position);
	}

	public int getCount() {
		return arrayitms.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class Fila {
		TextView name;
		ImageView icono;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		Fila view;
		
		LayoutInflater inflator = activity.getLayoutInflater();
		if (convertView == null) {
			view = new Fila();

			Item_request_friend itemRequestFriend = arrayitms.get(position);
			convertView = inflator.inflate(
					R.layout.item_request_friend, null);
			
			view.name = (TextView) convertView
					.findViewById(R.id.tv_item_request_friend_name);


			view.icono = (ImageView) convertView.findViewById(R.id.iv_item_avatar);
			
			view.name.setText(itemRequestFriend.getName());
			view.icono.setImageResource(itemRequestFriend.getIcono());
			
			convertView.setTag(view);
			
		} else {
			view = (Fila) convertView.getTag();
		}
		return convertView;
	}
}

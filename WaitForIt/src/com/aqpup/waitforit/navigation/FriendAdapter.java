package com.aqpup.waitforit.navigation;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqpup.waitforit.R;

public class FriendAdapter extends BaseAdapter {

	private Activity activity;
	private List<Item_friend> arrayitms;
	private Context context;

	public FriendAdapter(Activity activity, Context context,  List<Item_friend> listarry) {
		super();
		this.activity = activity;
		this.arrayitms = listarry;
		this.context = context;
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
		TextView address;
		ImageView icono;
		LinearLayout row;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Fila view;

		LayoutInflater inflator = activity.getLayoutInflater();
		if (convertView == null) {
			view = new Fila();

			final Item_friend itemFriend = arrayitms.get(position);
			convertView = inflator.inflate(R.layout.item_friend, null);

			view.name = (TextView) convertView
					.findViewById(R.id.tv_item_friend_name);
			view.address = (TextView) convertView
					.findViewById(R.id.tv_item_friend_address);

			view.icono = (ImageView) convertView
					.findViewById(R.id.iv_item_avatar);
			
			view.row = (LinearLayout) convertView
					.findViewById(R.id.llRowFriend);

			view.name.setText(itemFriend.getName());
			view.address.setText(itemFriend.getAddress());
			view.icono.setImageResource(itemFriend.getIcono());
			view.row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					

					
					// TODO Auto-generated method stub
					Intent aIntent = new Intent(v.getContext(), ChatActivity.class);
					//aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					aIntent.putExtra("targetUserId",	itemFriend.getTargetUserId());
					aIntent.putExtra("targetUsername", itemFriend.getName());
					v.getContext().startActivity(aIntent);
					
				}
			});

			convertView.setTag(view);

		} else {
			view = (Fila) convertView.getTag();
		}
		return convertView;
	}
}

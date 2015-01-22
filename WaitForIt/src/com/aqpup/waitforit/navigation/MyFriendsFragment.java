package com.aqpup.waitforit.navigation;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.Friendship;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.google.android.gms.internal.fo;
import com.google.api.services.storage.Storage.BucketAccessControls.List;

public class MyFriendsFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	private ListView lvFriends;
	private TextView tvUsername;
	private CustomLoginUser customLoginUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 setHasOptionsMenu(true);

		View v = inflater.inflate(R.layout.fragment_friends, container, false);

		lvFriends = (ListView) v.findViewById(R.id.lv_friends);
		// tvUsername = (TextView) v.findViewById(R.id.tv_full_name);
		tvUsername = (TextView) v.findViewById(R.id.tv_username);
		

		customLoginUser = CustomLoginUser.getInstanceCustomLoginUser();
		customLoginUser.setActivity(getActivity());
		customLoginUser.setContext(v.getContext());

		customLoginUser.openSharedPreferences();

		Long idUser = customLoginUser.getSh_Pref().getLong("usuarioId", 0);

		tvUsername.setText(customLoginUser.getSh_Pref().getString("userName",
				null));

		if (idUser != 0) {
			customLoginUser.new TaskSearchFriends(lvFriends, idUser).execute();
		}

		return v;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.menu_myfriends_fragment, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		
		if (id == R.id.action_request_friend) {
		
			CustomDialog.showDialogListRequestFriend(getActivity(), "Lista de Peticiones de Amigos", "fdds", 0);
			
			return true;
		}
		else if (id == R.id.action_add_friend) {
			
			CustomDialog.showDialogAddFriend(getActivity(),"Añadir usuario", "probando", 0);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
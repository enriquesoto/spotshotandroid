package com.aqpup.waitforit.utils;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqpup.waitforit.R;
import com.aqpup.waitforit.navigation.Item_request_friend;
import com.aqpup.waitforit.navigation.RequestFriendAdapter;

public class CustomDialog {

	private static int optionSelected;
	private static  boolean optionEULA;

	public static class Loading {
   
		private static Dialog dialog;
		private static ProgressBar pbLoading;
		
		public static void showDialogLoading(Context context, String title,
				String message, int icon) {

			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_loading);
			dialog.getWindow().setLayout(400, LayoutParams.WRAP_CONTENT);

			TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
			tvTitle.setText(title);
			tvTitle.setTypeface(CustomFont.getTfFontGeneral());

			pbLoading = (ProgressBar) dialog.findViewById(R.id.pbarLoading);
			pbLoading.setProgress(0);
			

			dialog.show();
		}

		public static Dialog getDialog() {
			return dialog;
		}

		public static void setDialog(Dialog dialog) {
			Loading.dialog = dialog;
		}

		public static ProgressBar getPbLoading() {
			return pbLoading;
		}

		public static void setPbLoading(ProgressBar pbLoading) {
			Loading.pbLoading = pbLoading;
		}		
		
	}

	public static void showDialogEula(Context context, String title,
			String message, final CheckBox cbOption) {

		final Dialog dialog = new Dialog(context);
	
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_eula);

		TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
		tvTitle.setText(title);
		tvTitle.setTypeface(CustomFont.getTfFontGeneral());

		TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
		tvMessage.setText(message);
		tvMessage.setTypeface(CustomFont.getTfFontGeneral());

		Button bAccept = (Button) dialog.findViewById(R.id.bAccept);

		bAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//optionEULA = true;
				cbOption.setChecked(true);
				dialog.dismiss();
			}
		});

		Button bCancel = (Button) dialog.findViewById(R.id.bCancel);

		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//optionEULA = false;
				cbOption.setChecked(false);
				dialog.dismiss();
			}
		});

		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialog.show();

	}

	public static void showDialogAlert(Context context, String title,
			String message, int icon) {

		final Dialog dialog = new Dialog(context);
		

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_alert);

		TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
		tvTitle.setText(title);
		tvTitle.setTypeface(CustomFont.getTfFontGeneral());

		TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
		tvMessage.setText(message);
		tvMessage.setTypeface(CustomFont.getTfFontGeneral());

		ImageView ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);
		ivIcon.setImageResource(icon);

		Button bAccept = (Button) dialog.findViewById(R.id.bAccept);

		bAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.getWindow().setLayout(450, LayoutParams.WRAP_CONTENT);
		dialog.show();

	}
	
	public static void showDialogAddFriend(final Context context, String title,
			String message, int icon) {

		final Dialog dialog = new Dialog(context);
		

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_friend);

		final EditText etUsername = (EditText) dialog.findViewById(R.id.etAddUsername);
		//tvTitle.setText(title);
		//tvTitle.setTypeface(CustomFont.getTfFontGeneral());


		Button bAddUsername = (Button) dialog.findViewById(R.id.bAddUsername);

		bAddUsername.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
								
				CustomLoginUser customLoginUser =  CustomLoginUser.getInstanceCustomLoginUser();
				
				customLoginUser.setContext(context);
				customLoginUser.openSharedPreferences();
				Long idUser = customLoginUser.getSh_Pref().getLong("usuarioId", 0);

				customLoginUser.new TaskAddFriend(etUsername.toString(),idUser);
				
			}
		});

		Button bCancel = (Button) dialog.findViewById(R.id.bCancel);

		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.getWindow().setLayout(450, LayoutParams.WRAP_CONTENT);
		dialog.show();

	}

	public static void showDialogListRequestFriend(Context context, String title,
			String message, int icon) {

		final Dialog dialog = new Dialog(context);
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_list_request_friend);

		final ListView lvRequestFriend = (ListView) dialog.findViewById(R.id.lvListRequestFriend);
		//tvTitle.setText(title);
		//tvTitle.setTypeface(CustomFont.getTfFontGeneral());
		
		CustomLoginUser customLoginUser = CustomLoginUser.getInstanceCustomLoginUser();
		customLoginUser.setContext(context);
		customLoginUser.openSharedPreferences();

		Long idUser = customLoginUser.getSh_Pref().getLong("usuarioId", 0);
		customLoginUser.new TaskRequestFriend(lvRequestFriend, idUser);
		
		dialog.getWindow().setLayout(450, LayoutParams.WRAP_CONTENT);
		dialog.show();

	}
	
	public static void showDialogList(Context context, String title,
			String[] options, final TextView tvGender) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_list);
		dialog.getWindow().setLayout(400, LayoutParams.WRAP_CONTENT);

		// TextView tvTitle= (TextView) dialog.findViewById(R.id.tvTitle);
		// tvTitle.setText(title);
		// tvTitle.setTypeface(CustomFont.getTfFontApp());

		LinearLayout llContentList = (LinearLayout) dialog
				.findViewById(R.id.llContentList);

		for (int i = 0; i < options.length; ++i) {

			TextView tvOption = new TextView(context);

			// tvOption.setBackground(context.getResources().getDrawable(R.drawable.dialog_custom_option));
			tvOption.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.xml__dialog_custom_option));
			tvOption.setText(options[i]);
			tvOption.setTextSize(20.0f);
			tvOption.setPadding(20, 20, 20, 20);

			tvOption.setTag(-1, i);

			tvOption.setTextColor(context.getResources().getColor(
					R.color.gray_ss));
			tvOption.setTypeface(CustomFont.getTfFontGeneral());

			tvOption.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			tvOption.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					 optionSelected = (Integer) ((TextView) v).getTag(-1);
					tvGender.setText(((TextView) v).getText().toString());
					dialog.dismiss();

				}
			});

			llContentList.addView(tvOption);

			// if(i != options.length-1){

			// tvOption.setPadding(0, 0, 0, 15);
			// }
		}

		/*
		 * ImageView ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);
		 * ivIcon.setImageResource(icon);
		 * 
		 * Button bAccept = (Button) dialog .findViewById(R.id.bAccept);
		 * 
		 * bAccept.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { dialog.dismiss(); } });
		 */
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();

	}

	public static int answerDialogList() {
		return optionSelected;
	}

	public static boolean answerDialogEULA() {
		return optionEULA;
	}
	
}

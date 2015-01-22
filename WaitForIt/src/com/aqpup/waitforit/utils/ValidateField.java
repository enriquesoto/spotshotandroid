package com.aqpup.waitforit.utils;

import java.util.Calendar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

public class ValidateField {

	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}
	
	public static TextWatcher getTextWatcherBirthday(final EditText etBirthday){
		
		TextWatcher tvBirthday = new TextWatcher() {

			private String current = "";
			private String aaaammdd = "AAAAMMDD";
			private Calendar cal = Calendar.getInstance();

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (!s.toString().equals(current)) {
					String clean = s.toString().replaceAll("[^\\d.]", "");
					String cleanC = current.replaceAll("[^\\d.]", "");

					int cl = clean.length();
					int sel = cl;
					for (int i = 2; i <= cl && i < 6; i += 2) {
						sel++;
					}
					// Fix for pressing delete next to a forward slash
					if (clean.equals(cleanC))
						sel--;

					if (clean.length() < 8) {
						clean = clean + aaaammdd.substring(clean.length());
					} else {
						// This part makes sure that when we finish entering
						// numbers
						// the date is correct, fixing it otherwise
						int day = Integer.parseInt(clean.substring(6, 8));
						int mon = Integer.parseInt(clean.substring(4, 6));
						int year = Integer.parseInt(clean.substring(0, 4));

						if (mon > 12)
							mon = 12;
						cal.set(Calendar.MONTH, mon - 1);
						day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal
								.getActualMaximum(Calendar.DATE) : day;
						year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
						clean = String.format("%02d%02d%02d", year, mon, day);
					}

					clean = String.format("%s-%s-%s", clean.substring(0, 4),
							clean.substring(4, 6), clean.substring(6, 8));
					current = clean;
					etBirthday.setText(current);
					etBirthday.setSelection(sel < current.length() ? sel : current
							.length());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		};
		
		return tvBirthday;
	}
}
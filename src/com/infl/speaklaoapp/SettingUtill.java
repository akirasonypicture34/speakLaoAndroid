package com.infl.speaklaoapp;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class SettingUtill {

	

	public static float getMediaVolume(Context context) {
		float volumeSet1;
		volumeSet1 = (float) (1 - (Math.log(50 - getVolume(context)) / Math
				.log(50)));
		return volumeSet1;
	}

	

	

	public static void saveBgMusic(Context c, String bgmusic) {
		SharedPreferences sp = c.getSharedPreferences("bgmusic",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("bgmusic", bgmusic);
		e.commit();
	}

	public static String getBgMusic(Context c) {
		SharedPreferences sp = c.getSharedPreferences("bgmusic",
				Context.MODE_PRIVATE);
		return sp.getString("bgmusic", "");

	}

	public static void saveVolume(Context c, int volume) {

		Log.e("volume1", volume + "");
		SharedPreferences sp = c.getSharedPreferences("volume_value",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putInt("volume_value", volume);
		e.commit();
	}

	public static int getVolume(Context c) {
		SharedPreferences sp = c.getSharedPreferences("volume_value",
				Context.MODE_PRIVATE);
		return sp.getInt("volume_value", 30);

	}

}

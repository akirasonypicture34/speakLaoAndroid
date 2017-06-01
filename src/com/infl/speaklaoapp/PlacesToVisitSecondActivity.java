package com.infl.speaklaoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesToVisitSecondActivity extends Activity implements
		OnClickListener {

	MediaPlayer mp, mpBg;
	int max_volume = 100;
	float volumeSet1;
	Context context;

	int[] images = { R.drawable.imagep1, R.drawable.imagep2,
			R.drawable.imagep3, R.drawable.imagep4, R.drawable.imagep5,
			R.drawable.imagep6, R.drawable.imagep7, R.drawable.imagep8,
			R.drawable.imagep9, R.drawable.imagep10, R.drawable.imagep11,
			R.drawable.imagep12, R.drawable.imagep13, R.drawable.imagep14,
			R.drawable.imagep15, R.drawable.imagep16, R.drawable.imagep17,
			R.drawable.imagep18 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_to_visit_next_activity);
		context = this;
		overridePendingTransition(0, 0);
		Bundle b = getIntent().getExtras();
		String name = b.getString("name");
		int pos = b.getInt("image");
		int songId = b.getInt("sound");

		((ImageView) findViewById(R.id.home_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);

		((ImageView) findViewById(R.id.image_full))
				.setBackgroundResource(images[pos]);

		((TextView) findViewById(R.id.txt_place)).setText(name);

		((ImageView) findViewById(R.id.image_back))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		if (mp != null) {
			try {
				mp.stop();
				mp.release();
			} catch (Exception e) {
				Log.w("Exception", e);
			}
		}
		try {
			mp = MediaPlayer.create(PlacesToVisitSecondActivity.this, songId);
			// mp.setVolume(volumeSet1, volumeSet1);
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (SettingUtill.getBgMusic(context).equalsIgnoreCase("1")) {
			try {
				mpBg = MediaPlayer.create(context, R.raw.battle);
				mpBg.setVolume(volumeSet1, volumeSet1);
				mpBg.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mp != null) {

			try {
				mp.release();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if (mpBg != null && mpBg.isPlaying()) {
				mpBg.stop();
				mpBg.release();
				mpBg = null;
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.gc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		if (id == R.id.home_btn) {
			Intent ih = new Intent(PlacesToVisitSecondActivity.this,
					HomeActivity.class);
			startActivity(ih);
		} else if (id == R.id.setting_btn) {
			Intent is = new Intent(PlacesToVisitSecondActivity.this,
					SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(PlacesToVisitSecondActivity.this,
					SearchActivity.class);
			startActivity(isearch);
		} else {
		}

	}

}

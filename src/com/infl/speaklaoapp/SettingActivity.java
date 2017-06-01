package com.infl.speaklaoapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SettingActivity extends Activity {

	SeekBar sb;
	Context context;
	MediaPlayer mp;
	int initialVol;

	int max_volume = 100;
	float volumeSet1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		context = this;

		overridePendingTransition(0, 0);

		sb = (SeekBar) findViewById(R.id.seekbar1);
		// sb.setProgress(35);

		initialVol = SettingUtill.getVolume(context);
		if (initialVol == 0) {
			sb.setProgress(30);
		} else {
			sb.setProgress(SettingUtill.getVolume(context));
		}
		Log.e("volume", "" + SettingUtill.getVolume(context));

		volumeSet1 = (float) (1 - (Math.log(max_volume
				- SettingUtill.getVolume(context)) / Math.log(max_volume)));

		if (SettingUtill.getBgMusic(context).equalsIgnoreCase("")
				|| SettingUtill.getBgMusic(context).equalsIgnoreCase("0")) {
			((ImageView) findViewById(R.id.toggleButton1))
					.setImageResource(R.drawable.switch_off1);
		} else {

			try {
				mp = MediaPlayer.create(context, R.raw.battle);
				mp.setVolume(volumeSet1, volumeSet1);
				mp.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

			((ImageView) findViewById(R.id.toggleButton1))
					.setImageResource(R.drawable.switch_on1);
		}

		((ImageView) findViewById(R.id.back_home_btn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						sb.getProgress();
						SettingUtill.saveVolume(context, sb.getProgress());

						finish();
					}
				});

		((ImageView) findViewById(R.id.toggleButton1))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (SettingUtill.getBgMusic(context).equalsIgnoreCase(
								"")
								|| SettingUtill.getBgMusic(context)
										.equalsIgnoreCase("0")) {
							SettingUtill.saveBgMusic(context, "1");
							((ImageView) findViewById(R.id.toggleButton1))
									.setImageResource(R.drawable.switch_on1);

							try {
								mp = MediaPlayer.create(context, R.raw.battle);
								mp.start();
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							SettingUtill.saveBgMusic(context, "0");
							((ImageView) findViewById(R.id.toggleButton1))
									.setImageResource(R.drawable.switch_off1);

							if (mp != null) {

								try {
									if (mp.isPlaying()) {
										mp.pause();
										mp.stop();
									}
									mp.release();
									Log.e("****ReLEASE Memo M6",
											"Release Memo***");
								} catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}

					}
				});

		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				// audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				// progress, 0);
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {

				// SettingUtill.saveVolume(context, progressChanged);
				// sb.getProgress();
				Toast.makeText(context, "Volume" + progressChanged,
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
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
			System.gc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

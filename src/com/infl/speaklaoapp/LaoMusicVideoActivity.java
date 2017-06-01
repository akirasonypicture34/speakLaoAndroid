package com.infl.speaklaoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LaoMusicVideoActivity extends Activity implements OnClickListener {

	Context context;
	MediaPlayer mpBg;
	int max_volume = 100;
	float volumeSet1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lao_videos_activity);
		context = this;
		overridePendingTransition(0, 0);

		volumeSet1 = (float) (1 - (Math.log(max_volume
				- SettingUtill.getVolume(context)) / Math.log(max_volume)));

		((ImageView) findViewById(R.id.video_1)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_2)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_3)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_4)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_5)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_6)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_7)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_8)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_9)).setOnClickListener(this);
		((ImageView) findViewById(R.id.video_10)).setOnClickListener(this);

		((ImageView) findViewById(R.id.home_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);

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
		int id = v.getId();
		if (id == R.id.video_1) {
			Intent i1 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i1.putExtra("video", 1);
			startActivity(i1);
		} else if (id == R.id.video_2) {
			Intent i2 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i2.putExtra("video", 2);
			startActivity(i2);
		} else if (id == R.id.video_3) {
			Intent i3 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i3.putExtra("video", 3);
			startActivity(i3);
		} else if (id == R.id.video_4) {
			Intent i4 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i4.putExtra("video", 4);
			startActivity(i4);
		} else if (id == R.id.video_5) {
			Intent i5 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i5.putExtra("video", 5);
			startActivity(i5);
		} else if (id == R.id.video_6) {
			Intent i6 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i6.putExtra("video", 6);
			startActivity(i6);
		} else if (id == R.id.video_7) {
			Intent i7 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i7.putExtra("video", 7);
			startActivity(i7);
		} else if (id == R.id.video_8) {
			Intent i8 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i8.putExtra("video", 8);
			startActivity(i8);
		} else if (id == R.id.video_9) {
			Intent i9 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i9.putExtra("video", 9);
			startActivity(i9);
		} else if (id == R.id.video_10) {
			Intent i10 = new Intent(LaoMusicVideoActivity.this,
					LaoMusicVideoSecondActivity.class);
			i10.putExtra("video", 10);
			startActivity(i10);
		} else if (id == R.id.home_btn) {
			Intent ih = new Intent(LaoMusicVideoActivity.this,
					HomeActivity.class);
			startActivity(ih);
		} else if (id == R.id.setting_btn) {
			Intent is = new Intent(LaoMusicVideoActivity.this,
					SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(LaoMusicVideoActivity.this,
					SearchActivity.class);
			startActivity(isearch);
		} else {
		}
	}

}

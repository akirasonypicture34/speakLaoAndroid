package com.infl.speaklaoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class LaoMusicVideoSecondActivity extends Activity implements
		OnClickListener {

	private VideoView myVideoView;
	private int position = 0;
	private MediaController mediaControls;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lao_videos_second_activity);
		context = this;

		overridePendingTransition(0, 0);

		myVideoView = (VideoView) findViewById(R.id.videoView1);
		((ImageView) findViewById(R.id.home_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.back_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.moreapp_btn)).setOnClickListener(this);

	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if (mediaControls == null) {
			mediaControls = new MediaController(
					LaoMusicVideoSecondActivity.this);
			mediaControls.setAnchorView(myVideoView);
		}

		try {
			playVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// video finish listener
		myVideoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// not playVideo
						// playVideo();

						finish();
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onResume();
		if (position > 0) {

			myVideoView.pause();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		overridePendingTransition(0, 0);
		
		if (position > 0) {

			myVideoView.start();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		try {
			System.gc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playVideo() {
		Bundle b = getIntent().getExtras();
		int vid = b.getInt("video");
		// getWindow().setFormat(PixelFormat.TRANSLUCENT);
		myVideoView.setMediaController(mediaControls);
		Uri video;

		if (vid == 1) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_1);
		} else if (vid == 2) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_2); // do not add any extension
		} else if (vid == 3) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_3); // do not add any extension
		} else if (vid == 4) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_4); // do not add any extension
		} else if (vid == 5) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_5); // do not add any extension
		} else if (vid == 6) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_6); // do not add any extension
		} else if (vid == 7) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_7); // do not add any extension
		} else if (vid == 8) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_8); // do not add any extension
		} else if (vid == 9) {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_9); // do not add any extension
		} else {
			video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.video_10); // do not add any extension
		}

		myVideoView.setVideoURI(video);
		myVideoView.requestFocus();
		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {

				myVideoView.seekTo(position);
				if (position == 0) {
					myVideoView.start();
				} else {
					myVideoView.start();
				}
			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		if (id == R.id.home_btn) {
			Intent ih = new Intent(LaoMusicVideoSecondActivity.this,
					HomeActivity.class);
			startActivity(ih);
		} else if (id == R.id.setting_btn) {
			Intent is = new Intent(LaoMusicVideoSecondActivity.this,
					SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(LaoMusicVideoSecondActivity.this,
					SearchActivity.class);
			startActivity(isearch);
		} else if (id == R.id.back_btn) {
			finish();
		} else if (id == R.id.moreapp_btn) {
			Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
			myWebLink.setData(Uri.parse("http://appforlao.com"));
			startActivity(myWebLink);
		} else {
		}

	}

}

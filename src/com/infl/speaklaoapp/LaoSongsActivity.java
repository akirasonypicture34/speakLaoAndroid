package com.infl.speaklaoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LaoSongsActivity extends Activity implements OnClickListener {

	ListView lv;
	Context context;
	MediaPlayer mp;
	MyAdapter myAdapter;
	int mark = 0;
	int pos = 0;

	int i = 0;

	int sound[] = { R.raw.audio1, R.raw.audio2, R.raw.audio3, R.raw.audio4,
			R.raw.audio5, R.raw.audio6, R.raw.audio7, R.raw.audio8,
			R.raw.audio9, R.raw.audio10, R.raw.audio11, R.raw.audio12,
			R.raw.audio13, R.raw.audio14, R.raw.audio15 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lao_songs_activity);
		context = this;

		overridePendingTransition(0, 0);
		if (mark == 0) {
			((ImageView) findViewById(R.id.play_pause_btn))
					.setBackgroundResource(R.drawable.btn_play);
			mark = 1;

		} else {
			((ImageView) findViewById(R.id.play_pause_btn))
					.setBackgroundResource(R.drawable.btn_stop);
			mark = 0;
		}

		String songsName[] = { "Alexandra Bounxouei", "L Zone", "Mone",
				"Pa Parina", "Noi Sengsouriga", "Xtreme", "Tom", "Pele",
				"Lao Dance", "Lao Hip Hop", "Lao Rock", "Jonny Olsen",
				"Alexandra Bounxouei", "Genii", "Shawty" };

		((ImageView) findViewById(R.id.home_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.play_back_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.play_pause_btn))
				.setOnClickListener(this);
		((ImageView) findViewById(R.id.play_next_btn)).setOnClickListener(this);

		lv = (ListView) findViewById(R.id.list_song);

		myAdapter = new MyAdapter(context, songsName);

		lv.setAdapter(myAdapter);

		play();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int songId = 0;
				if (mp != null) {
					try {
						mp.stop();
						mp.release();
					} catch (Exception e) {
						Log.w("Exception", e);
					}
				}

				mp = MediaPlayer.create(LaoSongsActivity.this, sound[position]);
				pos = position;
				// mp.setVolume(volumeSet1, volumeSet1);
				mp.start();

			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

	public void play() {

		if (mp != null) {
			try {
				mp.stop();
				mp.release();
			} catch (Exception e) {
				Log.w("Exception", e);
			}
		}

		mp = MediaPlayer.create(LaoSongsActivity.this, sound[i]);
		// mp.setVolume(volumeSet1, volumeSet1);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if (i < 15) {
					i = i + 1;
					play();
				}
			}
		});

	}

	/* My Adapter */
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		LayoutInflater mInflater;

		String[] songlist;

		public MyAdapter(Context context, String[] songlist) {

			mContext = context;
			mInflater = LayoutInflater.from(mContext);

			this.songlist = songlist;

		}

		class ViewHolder {

			public TextView txtSongName, txtNum;

		}

		public int getCount() {
			return songlist.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_song, null);
				ViewHolder holder = new ViewHolder();

				holder.txtSongName = (TextView) convertView
						.findViewById(R.id.text_song);

				holder.txtNum = (TextView) convertView
						.findViewById(R.id.text_num);

				convertView.setTag(holder);
			}

			final ViewHolder holder = (ViewHolder) convertView.getTag();

			Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(),
					"fonts/saysunil.ttf");
			holder.txtNum.setTypeface(typeFace);
			holder.txtSongName.setTypeface(typeFace);

			holder.txtNum.setText("" + (position + 1));
			holder.txtSongName.setText(songlist[position]);

			return convertView;

		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if (mp != null && mp.isPlaying()) {
				mp.stop();
				mp.release();
				mp = null;
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

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		if (id == R.id.home_btn) {
			Intent ih = new Intent(LaoSongsActivity.this, HomeActivity.class);
			startActivity(ih);
		} else if (id == R.id.setting_btn) {
			Intent is = new Intent(LaoSongsActivity.this, SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(LaoSongsActivity.this,
					SearchActivity.class);
			startActivity(isearch);
		} else if (id == R.id.play_back_btn) {
			if (pos != 0) {
				((ImageView) findViewById(R.id.play_back_btn)).setAlpha(1.0f);

				if (mp != null) {
					try {
						mp.stop();
						mp.release();
					} catch (Exception e) {
						Log.w("Exception", e);
					}
				}
				pos = pos - 1;

				mp = MediaPlayer.create(LaoSongsActivity.this, sound[pos]);
				// mp.setVolume(volumeSet1, volumeSet1);

				mp.start();
			} else {

				((ImageView) findViewById(R.id.play_back_btn)).setAlpha(0.5f);
			}
		} else if (id == R.id.play_pause_btn) {
			if (mark == 0) {
				((ImageView) findViewById(R.id.play_pause_btn))
						.setBackgroundResource(R.drawable.btn_play);
				mark = 1;

				try {
					if (mp.isPlaying() && mp != null) {
						mp.pause();
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				((ImageView) findViewById(R.id.play_pause_btn))
						.setBackgroundResource(R.drawable.btn_stop);
				mark = 0;

				try {
					if (mp != null && !mp.isPlaying()) {
						mp.start();
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * if (mp.isPlaying() && mp != null) { mp.pause(); } else if (mp
				 * != null && !mp.isPlaying()) {
				 * 
				 * mp.start(); }
				 */
			}
		} else if (id == R.id.play_next_btn) {
			if (pos >= 14) {
				pos = pos;

				((ImageView) findViewById(R.id.play_next_btn)).setAlpha(0.5f);
			} else {
				((ImageView) findViewById(R.id.play_next_btn)).setAlpha(1.0f);
				if (mp != null) {
					try {
						mp.stop();
						mp.release();
					} catch (Exception e) {
						Log.w("Exception", e);
					}
				}
				pos = pos + 1;

				mp = MediaPlayer.create(LaoSongsActivity.this, sound[pos]);
				// mp.setVolume(volumeSet1, volumeSet1);

				mp.start();

			}
		} else {
		}

	}
}

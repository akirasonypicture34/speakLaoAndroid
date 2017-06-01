package com.infl.speaklaoapp;

import java.util.ArrayList;
import java.util.HashMap;
import com.infl.database.DBAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DirectionAndTransportActivity extends Activity implements
		OnClickListener {

	Context context;
	ListView listview;
	MediaPlayer mp, mpBg;
	MyAdapter myAdapter;

	int max_volume = 100;
	float volumeSet1;

	ArrayList<String> englishWordList = new ArrayList<String>();
	ArrayList<String> thaiWordList = new ArrayList<String>();
	ArrayList<String> proWordList = new ArrayList<String>();
	ArrayList<String> soundList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.direction_and_transport_activity);
		context = this;

		overridePendingTransition(0, 0);

		listview = (ListView) findViewById(R.id.list_dir_and_trans);

		((ImageView) findViewById(R.id.home_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);

		volumeSet1 = (float) (1 - (Math.log(max_volume
				- SettingUtill.getVolume(context)) / Math.log(max_volume)));

		getData();

		listview.setOnItemClickListener(new OnItemClickListener() {

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

				try {
					String song = soundList.get(position).replace("R.raw.", "")
							.replace("\n", "");
					songId = getAllResourceIDs(R.raw.class, song);
					mp = MediaPlayer.create(DirectionAndTransportActivity.this,
							songId);
					// mp.setVolume(volumeSet1, volumeSet1);
					mp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	// function which returns the unique resource ID.
	public static int getAllResourceIDs(Class c, String song)
			throws IllegalArgumentException {
		// System.out.println("inside HashMap"+ song);
		HashMap resmap = new HashMap();
		java.lang.reflect.Field[] fields = c.getFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				if (song != null)
					if (fields[i].getName().startsWith(song))
						resmap.put(fields[i].getName(), fields[i].getInt(null));
					else
						resmap.put(fields[i].getName(), fields[i].getInt(null));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		Integer one = (Integer) resmap.get(song);
		int songid = one.intValue();
		return songid;
	}

	private void getData() {
		// String resp = "";
		String chapter_name = "Direction and transport";
		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor cs = db.getBasicData(chapter_name);

		if (cs != null) {

			if (cs.moveToFirst()) {
				do {
					try {

						// resp = cs.getString(0);
						englishWordList.add(cs.getString(3));
						thaiWordList.add(cs.getString(4));
						proWordList.add(cs.getString(5));
						soundList.add(cs.getString(6));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} while (cs.moveToNext());

				cs.close();
			}

			myAdapter = new MyAdapter(context, englishWordList, thaiWordList,
					proWordList);

			listview.setAdapter(myAdapter);

		} else {

		}

		db.close();

	}

	/* My Adapter */
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		LayoutInflater mInflater;

		ArrayList<String> englishWordList = new ArrayList<String>();
		ArrayList<String> thaiWordList = new ArrayList<String>();
		ArrayList<String> proWordList = new ArrayList<String>();

		public MyAdapter(Context context, ArrayList<String> englishWordList,
				ArrayList<String> thaiWordList, ArrayList<String> proWordList) {

			mContext = context;
			mInflater = LayoutInflater.from(mContext);

			this.englishWordList = englishWordList;
			this.thaiWordList = thaiWordList;
			this.proWordList = proWordList;

		}

		class ViewHolder {

			public TextView txtEngWord, txtThaiWord, txtProWord;

		}

		public int getCount() {
			return englishWordList.size();
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
				convertView = mInflater.inflate(R.layout.row_one, null);
				ViewHolder holder = new ViewHolder();

				holder.txtEngWord = (TextView) convertView
						.findViewById(R.id.text_eng);
				holder.txtThaiWord = (TextView) convertView
						.findViewById(R.id.text_thai);
				holder.txtProWord = (TextView) convertView
						.findViewById(R.id.text_pro);

				convertView.setTag(holder);
			}

			final ViewHolder holder = (ViewHolder) convertView.getTag();

			Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(),
					"fonts/saysunil.ttf");
			holder.txtEngWord.setTypeface(typeFace);
			holder.txtThaiWord.setTypeface(typeFace);
			holder.txtProWord.setTypeface(typeFace);

			holder.txtEngWord.setText(englishWordList.get(position));
			holder.txtThaiWord.setText(thaiWordList.get(position));
			holder.txtProWord.setText(proWordList.get(position));

			return convertView;

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
			Intent ih = new Intent(DirectionAndTransportActivity.this,
					HomeActivity.class);
			startActivity(ih);
		} else if (id == R.id.setting_btn) {
			Intent is = new Intent(DirectionAndTransportActivity.this,
					SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(DirectionAndTransportActivity.this,
					SearchActivity.class);
			startActivity(isearch);
		} else {
		}

	}
}

package com.example.notifyuapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.notifyuapp.db.DBAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Favourites extends Activity {

	Context context;
	ImageView backBtn;
	ListView listView;
	BusinessAdapter myAdapter;

	ProgressDialog dialog;	

	ArrayList<String> Id = new ArrayList<String>();
	ArrayList<String> Title = new ArrayList<String>();
	ArrayList<String> Rating = new ArrayList<String>();
	ArrayList<String> Cuisine_type = new ArrayList<String>();
	ArrayList<String> Image_name = new ArrayList<String>();
	ArrayList<String> Distance_km = new ArrayList<String>();
	ArrayList<String> data_list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		setContentView(R.layout.favourites);
		listView = (ListView) findViewById(R.id.listView);
		
		dialog = ProgressDialog.show(context, "", "Please wait...");
		getFavoriteTask task = new getFavoriteTask();
		task.execute(new String[] {});
		

	}

	
	
	

	private void getFavData() {
		String resp = "";

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor cs = db.getDocumentData();

		if (cs != null) {

			if (cs.moveToFirst()) {
				do {
					try {

						resp = cs.getString(0);
						data_list.add(resp);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} while (cs.moveToNext());

				cs.close();
			}

		} else {
			
		}

		db.close();
		

	}

	private class getFavoriteTask extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... urls) {

			String response = "";

			try {
				getFavData();

			} catch (Exception e) {

				e.printStackTrace();
			}

			return response;
		}

		protected void onPostExecute(String result) {
			JSONObject jObject;
			try {			
				Log.e("Response", "**********" + result);		

				SharedPreferences sharedPreferences = getSharedPreferences(
						"base_url", MODE_PRIVATE);

				String baseUrl = sharedPreferences.getString("url", "");

				for (int i = 0; i < data_list.size(); i++) {
					String res = data_list.get(i);
					JSONObject jo = new JSONObject(res);

					Id.add(jo.getString("id"));
					Title.add(jo.getString("title"));					
					Rating.add(jo.getString("rating"));
					Cuisine_type.add(jo.getString("cuisine_type"));					
					Image_name.add(baseUrl + jo.getString("image_name"));				
					Log.e("Complete Image Url", Image_name.get(i));					
					Double km = Double.parseDouble(jo.getString("distance_km"));
					Double miles = AppUtil.convertKmToMi(km);
					Distance_km.add(miles.toString());

					Log.e("Json Object Data added: ", "" + i);
					myAdapter = new BusinessAdapter(context, Image_name,
							Rating, Distance_km, Title, Cuisine_type);
					listView.setAdapter(myAdapter);
				

				}
				if (dialog != null)
					dialog.cancel();

			} catch (Exception e) {
				e.printStackTrace();
				if (dialog != null)
					dialog.cancel();
			}
		}
	}

}

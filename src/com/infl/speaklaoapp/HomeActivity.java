package com.infl.speaklaoapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.infl.facebook.AsyncFacebookRunner;
import com.infl.facebook.DialogError;
import com.infl.facebook.Facebook;
import com.infl.facebook.Facebook.DialogListener;
import com.infl.facebook.FacebookError;
import com.infl.networkinfo.ConnectionDetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener {

	Context context;
	String path_to_share;
	MediaPlayer mpBg;

	int max_volume = 100;
	float volumeSet1;

	// for facebook share...
	private SharedPreferences prefs;
	private Facebook facebook;
	private AsyncFacebookRunner mAsyncRunner;
	ProgressDialog pDialog, dialog;

	// Internet Connection detector
	private ConnectionDetector cd;

	private static String APP_ID = "461245193945112"; // for Speak Lao App

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		
		copyDbFile();

		facebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}

		((ImageView) findViewById(R.id.setting_btn)).setOnClickListener(this);
		
		((ImageView) findViewById(R.id.search_btn)).setOnClickListener(this);

		((ImageView) findViewById(R.id.basic_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.days_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.direction_transport_btn))
				.setOnClickListener(this);
		((ImageView) findViewById(R.id.lao_food_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.place_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.vocabulary_btn))
				.setOnClickListener(this);
		((ImageView) findViewById(R.id.months_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.numbers_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.place_to_visit_btn))
				.setOnClickListener(this);
		((ImageView) findViewById(R.id.lao_music_video_btn))
				.setOnClickListener(this);
		((ImageView) findViewById(R.id.lao_songs_btn)).setOnClickListener(this);

		((ImageView) findViewById(R.id.appforlao_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.rateforus_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.mail_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.facebook_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.twitter_btn)).setOnClickListener(this);
		((ImageView) findViewById(R.id.moreapps_btn)).setOnClickListener(this);

		volumeSet1 = (float) (1 - (Math.log(max_volume
				- SettingUtill.getVolume(context)) / Math.log(max_volume)));


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

	}

	public void shareOnFacebook() {

		try {
			Bundle parameters = new Bundle();

			/*
			 * parameters.putString("name", "VdoTrip");
			 * parameters.putString("link", video_url);
			 */
			/*
			 * parameters.putString("caption",
			 * business_name+"\n"+coupon_details);
			 * //parameters.putString("description", coupon_details);
			 * parameters.putString("picture", profile_image_url);
			 */

			parameters.putString("name", "Speak Lao Language");
			parameters.putString("link", "https://www.facebook.com/AppForLao");
			parameters.putString("caption", "Great One");
			parameters
					.putString(
							"description",
							"Hi, I brought this Learn to speak Lao language app, it's helpful tool to learn Lao Language");
			parameters.putString("picture","http://inflectica.com/share_img.jpg");

			facebook.dialog(this, "feed", parameters, new DialogListener() {

				public void onFacebookError(FacebookError e) {
				}

				public void onError(DialogError e) {
				}

				public void onComplete(Bundle values) {

					Set<String> data = values.keySet();
					Log.e("bundle data", data.toString());
					http: // inflectica.com/share_img.jpg
					if (values != null) {
						try {
							if (!values.getString("post_id").equalsIgnoreCase(
									"")) {

								// Toast.makeText(getBaseContext(),
								// "onComplete", Toast.LENGTH_LONG).show();
								// shareOnServer();
							}

						} catch (Exception e) {
							e.printStackTrace();
							// Toast.makeText(getBaseContext(), "Cancel",
							// Toast.LENGTH_LONG).show();

						}
					}

				}

				public void onCancel() {
					// Toast.makeText(getBaseContext(), "onCancel",
					// Toast.LENGTH_LONG).show();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		createBitMap();

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

	// www.appforlao.com open browser
	// dialog box title-Please give us a feedback. message-If you like and learn
	// fron this app,please rate it in the play so we can improve and develope
	// more apps.We appreciate for your time.
	// Thanks for support!
	// button- Maybe Later, Rate It Now

	private void copyDbFile() {
		SharedPreferences marker = getSharedPreferences("mark", 0);
		int mark = marker.getInt("mark", 1);
		if (mark == 1) {
			copyDb();
			SharedPreferences.Editor editor = marker.edit();
			editor.putInt("mark", 0);
			editor.commit();
		}

	}

	private void copyDb() {
		try {

			// Toast.makeText(this, "going to copy db",
			// Toast.LENGTH_SHORT).show();

			String directoryPath = "/data/data/" + getPackageName()
					+ "/databases";
			File directory = new File(directoryPath);
			// Toast.makeText(this, "directory = " +
			// directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
			Log.d("FILECOPY", "directory = " + directory.getAbsolutePath());
			// Toast.makeText(this, "directory.exists() = " +
			// directory.exists(), Toast.LENGTH_SHORT).show();
			Log.d("FILECOPY", "directory.exists() = " + directory.exists());

			if (!directory.exists()) {
				boolean checkCreation = directory.mkdirs();
				if (!checkCreation) {
					throw new Exception("Directory" + directoryPath
							+ " not created");
				}
			}

			File file = new File(directory, "SpeakLaoDB");
			if (file.exists()) {
				// Toast.makeText(this, "Deleting file that already exists: " +
				// file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
				Log.d("FILECOPY",
						"Deleting file that already exists: "
								+ file.getAbsolutePath());
				file.delete();
				file.createNewFile();
			}

			FileOutputStream outstream = new FileOutputStream(file);

			InputStream is = getBaseContext().getAssets().open("SpeakLaoDB");

			int bytesRead = 0;
			byte[] b = new byte[2048];

			while ((bytesRead = is.read(b)) != -1) {
				outstream.write(b, 0, bytesRead);
			}
			outstream.flush();
			outstream.close();
			if (is != null) {
				is.close();
			}

			Log.d("FILECOPY", "Copied database over" + file.getAbsolutePath());
			// Toast.makeText(this, "Copied database over",
			// Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Log.d("FILECOPY", "Error in copying over database");
			e.printStackTrace();
		}
	}

	void share(String nameApp, String imagePath, String message) {
		try {
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			Intent share = new Intent(android.content.Intent.ACTION_SEND);
			share.setType("image/jpg");

			List<ResolveInfo> resInfo = getPackageManager()
					.queryIntentActivities(share, 0);
			if (!resInfo.isEmpty()) {
				for (ResolveInfo info : resInfo) {
					Intent targetedShare = new Intent(
							android.content.Intent.ACTION_SEND);
					targetedShare.setType("image/jpg"); // put here your mime
														// type
					if (info.activityInfo.packageName.toLowerCase().contains(
							nameApp)
							|| info.activityInfo.name.toLowerCase().contains(
									nameApp)) {
						targetedShare.putExtra(Intent.EXTRA_SUBJECT,
								"Learn to speak Lao Language for Android");
						targetedShare.putExtra(Intent.EXTRA_TEXT, message);
						targetedShare.putExtra(Intent.EXTRA_STREAM,
								Uri.fromFile(new File(imagePath)));
						targetedShare.setPackage(info.activityInfo.packageName);
						targetedShareIntents.add(targetedShare);
					}
				}
				Intent chooserIntent = Intent.createChooser(
						targetedShareIntents.remove(0), "Select app to share");
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						targetedShareIntents.toArray(new Parcelable[] {}));
				startActivity(chooserIntent);
			}
		} catch (Exception e) {
			Log.v("VM",
					"Exception while sending image on" + nameApp + " "
							+ e.getMessage());

			Toast.makeText(
					context,
					nameApp.toUpperCase() + " is not installed in your device.",
					Toast.LENGTH_SHORT).show();

		}
	}

	public void createBitMap() {
		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
				R.drawable.share_img);
		String file_path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/SpeakLao";
		File dir = new File(file_path);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dir, "share_img.png");
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file);
			largeIcon.compress(Bitmap.CompressFormat.PNG, 35, fOut);
			fOut.flush();
			fOut.close();
			path_to_share = file.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void rateUs() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		// set title
		alertDialogBuilder.setTitle("Please give us a feedback");
		// set dialog message
		alertDialogBuilder
				.setMessage(
						"If you like & learn from this app, please rate on the Google Play so we can improve and develope more apps. We appreciate for your time.Thanks for support!")
				.setCancelable(false)
				.setPositiveButton("Rate It Now",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri
										.parse("http://appforlao.com/apps"));
								startActivity(myWebLink);
							}
						})
				.setNegativeButton("Maybe Later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.setting_btn) {
			Intent is = new Intent(HomeActivity.this, SettingActivity.class);
			startActivity(is);
		} else if (id == R.id.search_btn) {
			Intent isearch = new Intent(HomeActivity.this, SearchActivity.class);
			startActivity(isearch);
		} else if (id == R.id.basic_btn) {
			Intent ib = new Intent(HomeActivity.this, BasicActivity.class);
			startActivity(ib);
		} else if (id == R.id.days_btn) {
			Intent iday = new Intent(HomeActivity.this, DaysActivity.class);
			startActivity(iday);
		} else if (id == R.id.direction_transport_btn) {
			Intent idnt = new Intent(HomeActivity.this,
					DirectionAndTransportActivity.class);
			startActivity(idnt);
		} else if (id == R.id.lao_food_btn) {
			Intent ifood = new Intent(HomeActivity.this, LaoFoodActivity.class);
			startActivity(ifood);
		} else if (id == R.id.place_btn) {
			Intent iplaces = new Intent(HomeActivity.this, PlacesActivity.class);
			startActivity(iplaces);
		} else if (id == R.id.vocabulary_btn) {
			Intent ivocab = new Intent(HomeActivity.this,
					VocabularyActivity.class);
			startActivity(ivocab);
		} else if (id == R.id.months_btn) {
			Intent imonth = new Intent(HomeActivity.this, MonthsActivity.class);
			startActivity(imonth);
		} else if (id == R.id.numbers_btn) {
			Intent inum = new Intent(HomeActivity.this, NumbersActivity.class);
			startActivity(inum);
		} else if (id == R.id.place_to_visit_btn) {
			Intent iptv = new Intent(HomeActivity.this,
					PlacesToVisitActivity.class);
			startActivity(iptv);
		} else if (id == R.id.lao_music_video_btn) {
			Intent ilmv = new Intent(HomeActivity.this,
					LaoMusicVideoActivity.class);
			startActivity(ilmv);
		} else if (id == R.id.lao_songs_btn) {
			Intent isongs = new Intent(HomeActivity.this,
					LaoSongsActivity.class);
			startActivity(isongs);
		} else if (id == R.id.appforlao_btn) {
			Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
			myWebLink.setData(Uri.parse("http://appforlao.com"));
			startActivity(myWebLink);
		} else if (id == R.id.rateforus_btn) {
			rateUs();
		} else if (id == R.id.mail_btn) {
			if (isNetworkAvailable()) {
				try {
					//createBitMap();
					share("gmail",
							path_to_share,
							"Hi,\n I brought this app how to learn to speak Lao language. It's usefull tool to learn Lao Language and very easy \n Speak Lao Language: Click here to download \n http://www.appforlao.com/apps/speak-lao-language/");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
				final AlertDialog alertD = new AlertDialog.Builder(
						HomeActivity.this).create();

				alertD.setTitle("No Internet Connection");

				alertD.setMessage("You don't have internet connection.");

				alertD.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						alertD.dismiss();
					}
				});

				alertD.show();

			}
		} else if (id == R.id.facebook_btn) {
			if (isNetworkAvailable()) {
				try {
					//createBitMap();

					shareOnFacebook();
					// share("facebook", path_to_share, "http://appforlao.com");
					// share("facebook",
					// path_to_share,
					// "Hi,i brought this app as speak Lao Language,I think it's very useful tool to learn Lao Language as words/phrases \n http://www.appforlao.com/apps");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
				final AlertDialog alertD = new AlertDialog.Builder(
						HomeActivity.this).create();

				alertD.setTitle("No Internet Connection");

				alertD.setMessage("You don't have internet connection.");

				alertD.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						alertD.dismiss();
					}
				});

				alertD.show();

			}
		} else if (id == R.id.twitter_btn) {
			if (isNetworkAvailable()) {
				try {
					//createBitMap();
					share("com.twitter.android",
							path_to_share,
							"Hi, I brought this Learn to peak Lao language app, it's helpful tool to learn Lao Language");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
				final AlertDialog alertD = new AlertDialog.Builder(
						HomeActivity.this).create();

				alertD.setTitle("No Internet Connection");

				alertD.setMessage("You don't have internet connection.");

				alertD.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						alertD.dismiss();
					}
				});

				alertD.show();

			}
		} else if (id == R.id.moreapps_btn) {
			Intent myWebLink2 = new Intent(android.content.Intent.ACTION_VIEW);
			myWebLink2.setData(Uri.parse("http://appforlao.com/apps"));
			startActivity(myWebLink2);
		} else {
		}
	}

	// Network Availability
	// **********************************************************************************************
	public boolean isNetworkAvailable() {
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	// *********************************************************************************************************************

}

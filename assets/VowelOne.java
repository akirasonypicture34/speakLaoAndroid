package com.laoalphabet.vowel;

import java.io.IOException;
import java.io.InputStream;

import com.laoalphabet.app.MainActivity;
import com.laoalphabet.app.R;
import com.laoalphabet.app.SettingUtill;
import com.laoalphabet.app.SettingsActivity;
import com.laoalphabet.compoundconsonants.CompoundConsonantsTwo;
import com.laoalphabet.consonants.ConsonantsOne;
import com.laoalphabet.consonants.ConsonantsTwo;

import eu.andlabs.tutorial.animatedgifs.views.AnimEnd;
import eu.andlabs.tutorial.animatedgifs.views.GifMovieView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VowelOne extends Activity implements OnClickListener, AnimEnd {
	MediaPlayer mp1, mp2, mp3, mp4, mp5, mp6, mp7, mp8, mp9;
	RelativeLayout animImg1, animImg2;
	Context context;
	int max_volume = 50;
	float volumeSet1;

	// integer arrays for sound voice

	int[] arrayTitleSound = { R.raw.v_28vowels_boy, R.raw.v_28vowels_woman,
			R.raw.v_28vowels_boy, R.raw.v_28vowels_girl };

	int[] arraySWS = { R.raw.v_short_vowel_sound_man,
			R.raw.v_short_vowel_sound_woman, R.raw.v_short_vowel_sound_boy,
			R.raw.v_short_vowel_sound_girl };

	int[] arrayLWS = { R.raw.v_long_vowel_sound_man,
			R.raw.v_long_vowel_sound_woman, R.raw.v_long_vowel_sound_boy,
			R.raw.v_long_vowel_sound_girl };

	int[] arrayAlphabet1 = { R.raw.v_pickup_truck_alpha_man,
			R.raw.v_pickup_truck_alpha_woman, R.raw.v_pickup_truck_alpha_boy,
			R.raw.v_pickup_truck_alpha_girl };

	int[] arrayAlphabet2 = { R.raw.v_lao_style_fish_sauce_alpha_man,
			R.raw.v_lao_style_fish_sauce_alpha_woman,
			R.raw.v_lao_style_fish_sauce_alpha_boy,
			R.raw.v_lao_style_fish_sauce_alpha_girl };

	int arrayWord1[] = { R.raw.v_pickup_truck_man, R.raw.v_pickup_truck_woman,
			R.raw.v_pickup_truck_boy, R.raw.v_pickup_truck_girl };

	int arrayWord2[] = { R.raw.v_lao_style_fish_sauce_man,
			R.raw.v_lao_style_fish_sauce_woman,
			R.raw.v_lao_style_fish_sauce_boy, R.raw.v_lao_style_fish_sauce_girl };

	// text for sounds
	// TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vowel_screen1);
		context = this;
		overridePendingTransition(0, 0);
		AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		init();
		animImg1 = (RelativeLayout) findViewById(R.id.rl_anim_img1);
		animImg2 = (RelativeLayout) findViewById(R.id.rl_anim_img2);

		((ImageView) findViewById(R.id.btn_home)).setOnClickListener(this);
		((ImageView) findViewById(R.id.btn_setting)).setOnClickListener(this);
		((ImageView) findViewById(R.id.btn_back)).setOnClickListener(this);
		((ImageView) findViewById(R.id.btn_next)).setOnClickListener(this);

		((LinearLayout) findViewById(R.id.img_header)).setOnClickListener(this);
		((LinearLayout) findViewById(R.id.ll_head)).setOnClickListener(this);
		((LinearLayout) findViewById(R.id.ll_head2)).setOnClickListener(this);
		((LinearLayout) findViewById(R.id.img_32)).setOnClickListener(this);
		((LinearLayout) findViewById(R.id.img_42)).setOnClickListener(this);

		((TextView) findViewById(R.id.text7)).setOnClickListener(this);
		((TextView) findViewById(R.id.text8)).setOnClickListener(this);

		volumeSet1 = (float) (1 - (Math.log(max_volume
				- SettingUtill.getVolume(context)) / Math.log(max_volume)));

		setFisrstImage();
		set2Image();

		Log.e("index array",
				"************"
						+ arrayTitleSound[SettingUtill.getVoice(context)]);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		overridePendingTransition(0, 0);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Bundle b = getIntent().getExtras();
		if (b != null) {
			finish();
		} else {
			Intent iback = new Intent(VowelOne.this,
					CompoundConsonantsTwo.class);
			startActivity(iback);
			finish();
		}
		return;
	}

	public float getGifRatio() {
		int width;
		float gifRatio = 0.4f;
		width = animImg1.getWidth();
		try {
			if (width == 120) {
				gifRatio = (float) 0.4;
			} else if (width == 135) {
				gifRatio = (float) 0.45;
			} else if (width == 165) {
				gifRatio = (float) 0.55;
			} else if (width == 210) {
				gifRatio = (float) 0.7;
			} else if (width == 270) {
				gifRatio = (float) 0.9;
			} else if (width == 300) {
				gifRatio = (float) 1.0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gifRatio;

	}

	public void init() {

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/saysunib.ttf");

		((TextView) findViewById(R.id.text1)).setClickable(false);
		((TextView) findViewById(R.id.text2)).setClickable(false);
		((TextView) findViewById(R.id.text3)).setClickable(false);
		((TextView) findViewById(R.id.text4)).setClickable(false);
		((TextView) findViewById(R.id.text5)).setClickable(false);
		((TextView) findViewById(R.id.text6)).setClickable(false);
		((TextView) findViewById(R.id.text9)).setClickable(false);
		((TextView) findViewById(R.id.text10)).setClickable(false);
		((TextView) findViewById(R.id.text11)).setClickable(false);
		((TextView) findViewById(R.id.text12)).setClickable(false);

		((TextView) findViewById(R.id.text1)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text2)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text3)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text4)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text5)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text6)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text7)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text8)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text9)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text10)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text11)).setTypeface(typeFace);
		((TextView) findViewById(R.id.text12)).setTypeface(typeFace);

	}

	private void setFisrstImage() {

		ImageView image = new ImageView(this);
		image.setBackgroundResource(R.drawable.lao_truck_img);
		((RelativeLayout) findViewById(R.id.rl_anim_img1)).addView(image);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputStream stream = null;
				GifMovieView view;
				try {

					mp1 = MediaPlayer.create(VowelOne.this, R.raw.lao_truck);
					 mp1.setVolume(volumeSet1, volumeSet1);
					/*
					 * try { mp1.prepare(); } catch (IllegalStateException e) {
					 * // TODO Auto-generated catch block e.printStackTrace(); }
					 * catch (IOException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); }
					 */
					mp1.start();

					/*
					 * mp1.setOnCompletionListener(new OnCompletionListener() {
					 * public void onCompletion(MediaPlayer mp) { mp.release();
					 * 
					 * }; });
					 */
					stream = getAssets().open("anim_lao_truck.gif");
				} catch (IOException e) {
					e.printStackTrace();
				}
				view = new GifMovieView(getBaseContext(), stream,
						VowelOne.this, 511, getGifRatio());

				((RelativeLayout) findViewById(R.id.rl_anim_img1))
						.removeAllViews();

				((RelativeLayout) findViewById(R.id.rl_anim_img1))
						.addView(view);
			}
		});

	}

	private void set2Image() {

		ImageView image = new ImageView(this);
		image.setBackgroundResource(R.drawable.lao_style_fish);
		((RelativeLayout) findViewById(R.id.rl_anim_img2)).addView(image);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputStream stream = null;
				GifMovieView view;
				try {

					mp2 = MediaPlayer.create(VowelOne.this,
							R.raw.lao_style_fish_sauce);
					 mp2.setVolume(volumeSet1, volumeSet1);
					/*
					 * try { mp2.prepare(); } catch (IllegalStateException e) {
					 * // TODO Auto-generated catch block e.printStackTrace(); }
					 * catch (IOException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); }
					 */
					mp2.start();
					/*
					 * mp2.setOnCompletionListener(new OnCompletionListener() {
					 * public void onCompletion(MediaPlayer mp) { mp.release();
					 * 
					 * }; });
					 */
					stream = getAssets().open("anim_lao_style_fish_sauce.gif");
				} catch (IOException e) {
					e.printStackTrace();
				}
				view = new GifMovieView(getBaseContext(), stream,
						VowelOne.this, 512, getGifRatio());

				((RelativeLayout) findViewById(R.id.rl_anim_img2))
						.removeAllViews();

				((RelativeLayout) findViewById(R.id.rl_anim_img2))
						.addView(view);
			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (mp1 != null && mp1.isPlaying()) {
			mp1.stop();
			mp1.release();
			mp1 = null;

		}
		if (mp2 != null && mp2.isPlaying()) {
			mp2.stop();
			mp2.release();
			mp2 = null;

		}
		if (mp3 != null && mp3.isPlaying()) {
			mp3.stop();
			mp3.release();
			mp3 = null;

		}
		if (mp4 != null && mp4.isPlaying()) {
			mp4.stop();
			mp4.release();
			mp4 = null;

		}
		if (mp5 != null && mp5.isPlaying()) {
			mp5.stop();
			mp5.release();
			mp5 = null;

		}
		if (mp6 != null && mp6.isPlaying()) {
			mp6.stop();
			mp6.release();
			mp6 = null;

		}
		if (mp7 != null && mp7.isPlaying()) {
			mp7.stop();
			mp7.release();
			mp7 = null;

		}
		if (mp8 != null && mp8.isPlaying()) {
			mp8.stop();
			mp8.release();
			mp8 = null;

		}
		if (mp9 != null && mp9.isPlaying()) {
			mp9.stop();
			mp9.release();
			mp9 = null;

		}

		// Toast.makeText(context, "OnStop", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_home:

			Intent i = new Intent(VowelOne.this, MainActivity.class);
			startActivity(i);

			break;
		case R.id.btn_setting:

			Intent i2 = new Intent(VowelOne.this, SettingsActivity.class);
			startActivity(i2);

			break;
		case R.id.btn_back:

			Intent iback = new Intent(VowelOne.this,
					CompoundConsonantsTwo.class);
			startActivity(iback);
			finish();
			break;
		case R.id.btn_next:

			Intent i3 = new Intent(VowelOne.this, VowelTwo.class);
			startActivity(i3);
			finish();
			break;
		case R.id.img_header:

			if (mp3 != null) {

				mp3.stop();
				mp3.release();
			}
			// mp3 = MediaPlayer.create(VowelOne.this, arrayTitleSound[1]);
			mp3 = MediaPlayer.create(VowelOne.this,
					arrayTitleSound[SettingUtill.getVoice(context)]);
			 mp3.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp3.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			mp3.start();
			/*
			 * mp3.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */

			break;

		case R.id.ll_head:

			if (mp4 != null) {

				mp4.stop();
				mp4.release();
			}

			mp4 = MediaPlayer.create(VowelOne.this,
					arraySWS[SettingUtill.getVoice(context)]);
			// mp4.setVolume(0.99f, 0.99f);
			 mp4.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp4.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			mp4.start();

			/*
			 * mp4.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */

			break;
		case R.id.ll_head2:
			if (mp5 != null) {

				mp5.stop();
				mp5.release();
			}

			mp5 = MediaPlayer.create(VowelOne.this,
					arrayLWS[SettingUtill.getVoice(context)]);
			 mp5.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp5.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			mp5.start();

			/*
			 * mp5.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */

			break;

		case R.id.text7:

			if (mp6 != null) {

				mp6.stop();
				mp6.release();
			}

			mp6 = MediaPlayer.create(VowelOne.this,
					arrayAlphabet1[SettingUtill.getVoice(context)]);
			 mp6.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp6.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			mp6.start();

			/*
			 * mp6.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */
			break;
		case R.id.text8:

			if (mp7 != null) {

				mp7.stop();
				mp7.release();
			}

			mp7 = MediaPlayer.create(VowelOne.this,
					arrayAlphabet2[SettingUtill.getVoice(context)]);
			 mp7.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp7.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			mp7.start();

			/*
			 * mp7.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */

			break;
		case R.id.img_32:

			if (mp8 != null) {

				mp8.stop();
				mp8.release();
			}

			mp8 = MediaPlayer.create(VowelOne.this,
					arrayWord1[SettingUtill.getVoice(context)]);
			 mp8.setVolume(volumeSet1, volumeSet1);
			/*
			 * try { mp8.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			mp8.start();

			/*
			 * mp8.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */

			break;

		case R.id.img_42:

			if (mp9 != null) {

				mp9.stop();
				mp9.release();
			}

			mp9 = MediaPlayer.create(VowelOne.this,
					arrayWord2[SettingUtill.getVoice(context)]);
			 mp9.setVolume(volumeSet1, volumeSet1);

			/*
			 * try { mp9.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			mp9.start();
			/*
			 * mp9.setOnCompletionListener(new OnCompletionListener() { public
			 * void onCompletion(MediaPlayer mp) { mp.release();
			 * 
			 * }; });
			 */
			break;

		default:
			break;
		}

	}

	@Override
	public void animEnd(int requestId) {
		// TODO Auto-generated method stub
		if (requestId == 511) {
			if (mp1 != null && mp1.isPlaying()) {
				mp1.stop();
			}
			ImageView image = new ImageView(this);
			image.setBackgroundResource(R.drawable.lao_truck_img);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputStream stream = null;
					GifMovieView view;
					try {

						mp1 = MediaPlayer
								.create(VowelOne.this, R.raw.lao_truck);
						 mp1.setVolume(volumeSet1, volumeSet1);
						/*
						 * try { mp1.prepare(); } catch (IllegalStateException
						 * e) { // TODO Auto-generated catch block
						 * e.printStackTrace(); } catch (IOException e) { //
						 * TODO Auto-generated catch block e.printStackTrace();
						 * }
						 */
						mp1.start();
						/*
						 * mp1.setOnCompletionListener(new
						 * OnCompletionListener() { public void
						 * onCompletion(MediaPlayer mp) { mp.release();
						 * 
						 * }; });
						 */

						stream = getAssets().open("anim_lao_truck.gif");
					} catch (IOException e) {
						e.printStackTrace();
					}
					view = new GifMovieView(getBaseContext(), stream,
							VowelOne.this, 511, getGifRatio());

					((RelativeLayout) findViewById(R.id.rl_anim_img1))
							.removeAllViews();

					((RelativeLayout) findViewById(R.id.rl_anim_img1))
							.addView(view);
				}
			});
			((RelativeLayout) findViewById(R.id.rl_anim_img1)).removeAllViews();
			((RelativeLayout) findViewById(R.id.rl_anim_img1)).addView(image);
		} else if (requestId == 512) {
			if (mp2 != null && mp2.isPlaying()) {
				mp2.stop();
			}
			ImageView image = new ImageView(this);
			image.setBackgroundResource(R.drawable.lao_style_fish);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputStream stream = null;
					GifMovieView view;

					try {

						mp2 = MediaPlayer.create(VowelOne.this,
								R.raw.lao_style_fish_sauce);
						 mp2.setVolume(volumeSet1, volumeSet1);
						/*
						 * try { mp2.prepare(); } catch (IllegalStateException
						 * e) { // TODO Auto-generated catch block
						 * e.printStackTrace(); } catch (IOException e) { //
						 * TODO Auto-generated catch block e.printStackTrace();
						 * }
						 */
						mp2.start();
						/*
						 * mp2.setOnCompletionListener(new
						 * OnCompletionListener() { public void
						 * onCompletion(MediaPlayer mp) { mp.release();
						 * 
						 * }; });
						 */

						stream = getAssets().open(
								"anim_lao_style_fish_sauce.gif");
					} catch (IOException e) {
						e.printStackTrace();
					}
					view = new GifMovieView(getBaseContext(), stream,
							VowelOne.this, 512, getGifRatio());

					((RelativeLayout) findViewById(R.id.rl_anim_img2))
							.removeAllViews();

					((RelativeLayout) findViewById(R.id.rl_anim_img2))
							.addView(view);
				}
			});
			((RelativeLayout) findViewById(R.id.rl_anim_img2)).removeAllViews();
			((RelativeLayout) findViewById(R.id.rl_anim_img2)).addView(image);
		}
	}

}

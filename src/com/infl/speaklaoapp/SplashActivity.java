package com.infl.speaklaoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		new Handler().postDelayed(new Runnable() {

			// Using handler with postDelayed called runnable run method

			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this, HomeActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, 2 * 1000); // wait for 2 seconds

	}

	/*
	 * private void StartAnimations() { Animation anim =
	 * AnimationUtils.loadAnimation(this, R.anim.alpha); anim.reset();
	 * LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
	 * l.clearAnimation(); l.startAnimation(anim);
	 * 
	 * anim = AnimationUtils.loadAnimation(this, R.anim.translate);
	 * anim.reset(); ImageView iv = (ImageView) findViewById(R.id.logo);
	 * iv.clearAnimation(); iv.startAnimation(anim);
	 * 
	 * }
	 */
}

package com.infl.speaklaoapp;

import android.app.Activity;
import android.app.ListActivity;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;


public abstract class BaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
}

package com.infl.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.infl.facebook.AsyncFacebookRunner.RequestListener;

import android.util.Log;



public abstract class BaseRequestListener implements RequestListener{

	 public void onFacebookError(FacebookError e, final Object state) {
	        Log.e("Facebook", e.getMessage());
	        e.printStackTrace();
	    }

	    public void onFileNotFoundException(FileNotFoundException e,
	                                        final Object state) {
	        Log.e("Facebook", e.getMessage());
	        e.printStackTrace();
	    }

	    public void onIOException(IOException e, final Object state) {
	        Log.e("Facebook", e.getMessage());
	        e.printStackTrace();
	    }

	    public void onMalformedURLException(MalformedURLException e,
	                                        final Object state) {
	        Log.e("Facebook", e.getMessage());
	        e.printStackTrace();
	    }
}

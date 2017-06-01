package com.infl.facebook;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
public class FacebookStatusPost 
{
	Context FacebookStatusPostContext;
	FacebookConnector facebookConnector;
	String caption;
	String description;
	String titleName;
	String titleNameLink;
	String imageUrl;
	String msg;
	private final Handler mFacebookHandler = new Handler();
	public FacebookStatusPost(Context ctx,FacebookConnector fc, String message)
	{
		FacebookStatusPostContext = ctx;
		facebookConnector = fc;
		
		msg = message;
	}
	final Runnable mUpdateFacebookNotification = new Runnable() 
    {
        public void run() 
        {
        	Toast.makeText(FacebookStatusPostContext, "Facebook updated successfully", Toast.LENGTH_SHORT).show();
        	
        	/*
        	AlertDialog.Builder builder = new AlertDialog.Builder(FacebookStatusPostContext);
        	builder.setMessage("Facebook updated successfully.")
        	       .setCancelable(false)
        	       .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();
        	*/
		}
    };
    
	public void postMessage() 
	{
		
		if (facebookConnector.getFacebook().isSessionValid()) 
		{
			Log.e("","session valid");
			postMessageInThread();
		} 
		else 
		{
			Log.e("","session invalid");
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() 
			{
				public void onAuthSucceed() 
				{
					Log.e("","inside on oauth succeeded");
					postMessageInThread();
				}
				public void onAuthFail(String error) 
				{
					Log.e("","inside on oauth failed");
				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}
	}

	private void postMessageInThread() 
	{
		Thread t = new Thread() 
		{
			public void run() 
			{
		    	
		    	try 
		    	{
		    		Log.e("","final updation");
		    		if (facebookConnector.getFacebook().isSessionValid()) 
		    		{
		    			Log.e("??","**************"+description+"msg :"+msg);
		    			facebookConnector.postMessageOnWall(msg);
		    			mFacebookHandler.post(mUpdateFacebookNotification);
		    		}
					Log.e("","updation done");
				} 
		    	catch (Exception ex) 
		    	{
					Log.e("", "Error sending msg",ex);
				}
		    }
		};
		t.start();
	}
	
	
}
